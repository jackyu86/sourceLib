package com.caej.product.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.caej.insurance.api.InsuranceVendorWebService;
import com.caej.insurance.api.vendor.InsuranceVendorResponse;
import com.caej.order.order.OrderView;
import com.caej.product.api.ArchiveWebService;
import com.caej.product.api.archive.ArchiveGroup;
import com.caej.product.api.product.FormView;
import com.caej.product.domain.Product;
import com.caej.product.service.ProductFormService;
import com.caej.product.service.ProductService;
import com.caej.product.service.archive.ArchiveService;
import com.caej.util.UUIDUtil;
import com.google.common.collect.Maps;
import com.lowagie.text.pdf.BaseFont;

import io.sited.file.api.DirectoryWebService;
import io.sited.file.api.FileConfig;
import io.sited.file.api.FileWebService;
import io.sited.file.api.directory.CreateDirectoryRequest;
import io.sited.file.api.directory.DirectoryResponse;
import io.sited.file.api.file.FileRequest;
import io.sited.form.Form;
import io.sited.http.PathParam;
import io.sited.template.TemplateConfig;
import io.sited.template.TemplateContext;

/**
 * @author miller
 */
public class ArchiveWebServiceImpl implements ArchiveWebService {
    private final Logger logger = LoggerFactory.getLogger(ArchiveWebServiceImpl.class);
    @Inject
    ArchiveService archiveService;
    @Inject
    ProductFormService productFormService;
    @Inject
    ProductService productService;
    @Inject
    TemplateConfig templateConfig;
    @Inject
    InsuranceVendorWebService insuranceVendorWebService;
    @Inject
    FileConfig fileConfig;
    @Inject
    FileWebService fileWebService;
    @Inject
    DirectoryWebService directoryWebService;

    @Override
    public String archiveOrder(@PathParam("id") String id, OrderView orderView) {
        Product product = productService.get(new ObjectId(orderView.productId));
        InsuranceVendorResponse insuranceVendorResponse = insuranceVendorWebService.get(product.insuranceVendorId.toHexString());
        Form form = productFormService.form(product, orderView.form, true, "preview");
        FormView formView = FormView.view(form);
        List<ArchiveGroup> archiveGroups = archiveService.convertToArchive(form);
        String uuid = UUIDUtil.generate();
        String fileName = uuid + ".html";
        String pdf = uuid + ".pdf";
        TemplateContext templateContext = new TemplateContext();
        Map<String, Object> context = Maps.newHashMap();
        context.put("list", archiveGroups);
        context.put("product", product);
        context.put("id", product.id.toHexString());
        context.put("form", formView);
        String path;
        String imageName = uuid + ".jpg";
        File image = new File(imageName);
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")) {
            if (!image.exists()) {
                image.createNewFile();
            }
            downloadImage(insuranceVendorResponse.imageURL, imageName);
            context.put("imageUrl", imageName);
            templateContext.bindings = context;
            templateConfig.get("preview-direct.html").output(templateContext, outputStreamWriter);
            outputStreamWriter.flush();
            OutputStream os = new FileOutputStream(pdf);
            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont("msyh1.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.setDocument(new File(fileName));
            renderer.layout();
            renderer.createPDF(os);
            os.close();
            File pdfFile = new File(pdf);
            path = uploadFile(pdf, pdfFile);
            pdfFile.delete();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        File html = new File(fileName);
        if (html.exists()) {
            html.delete();
        }
        image.delete();
        return path;
    }

    private void downloadImage(String imageUrl, String imageName) throws IOException {
        //todo check url start with
        URL url = new URL(imageUrl);
        URLConnection con = url.openConnection();
        con.setConnectTimeout(5000);
        con.setRequestProperty("Accept", "*/*");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36");

        InputStream inputStream = con.getInputStream();
        byte[] bs = new byte[1024];
        int len;
        OutputStream outputStream = new FileOutputStream(imageName);
        while ((len = inputStream.read(bs)) != -1) {
            outputStream.write(bs, 0, len);
        }
        outputStream.close();
        inputStream.close();
    }

    private String uploadFile(String filename, File file) throws IOException {
        String path = "/upload/archive/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/" + UUIDUtil.generate() + ".pdf";
        try (InputStream inputStream = new FileInputStream(new File(filename))) {
            fileConfig.repository().put(path, inputStream);
        }
        CreateDirectoryRequest createDirectoryRequest = new CreateDirectoryRequest();
        createDirectoryRequest.path = path;
        createDirectoryRequest.requestBy = "archive";
        DirectoryResponse directoryResponse = directoryWebService.createAll(createDirectoryRequest);
        FileRequest fileRequest = new FileRequest();
        fileRequest.directoryId = directoryResponse.id;
        fileRequest.path = "/file" + path;
        fileRequest.length = file.length();
        fileRequest.title = filename;
        fileRequest.requestBy = "archive";
        return fileWebService.create(fileRequest).path;
    }
}

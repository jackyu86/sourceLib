package app;

import io.sited.db.Entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author chi
 */
public class EntityDoc {
    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    public static void main(String[] args) throws IOException {
        EntityDoc interfaceDoc = new EntityDoc();
        interfaceDoc.doc();
    }

    public static List<Class<?>> find(String scannedPackage) {
        String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        List<Class<?>> classes = new ArrayList<>();
        try {
            Enumeration<URL> scannedUrlSet = Thread.currentThread().getContextClassLoader().getResources(scannedPath);
            while (scannedUrlSet.hasMoreElements()) {
                URL scannedUrl = scannedUrlSet.nextElement();
                if (scannedUrl == null) {
                    throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
                }
                File scannedDir = new File(scannedUrl.getFile());
                if (scannedDir.isDirectory()) {
                    for (File file : scannedDir.listFiles()) {
                        classes.addAll(find(file, scannedPackage));
                    }
                } else {
                    ZipInputStream zip = new ZipInputStream(new FileInputStream(new File(scannedUrl.getFile().replace("!/" + scannedPath, "").replace("file:", ""))));
                    for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                        if (!entry.isDirectory() && entry.getName().endsWith(CLASS_FILE_SUFFIX)) {
                            // This ZipEntry represents a class. Now, what class does it represent?
                            String className = entry.getName().replace('/', '.'); // including ".class"
                            classes.add(Class.forName(className.substring(0, className.length() - ".class".length())));
                        }
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return classes;
    }

    private static List<Class<?>> find(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }

    public void doc() throws IOException {
        dealerDoc();
        cartDoc();
        esbDoc();
        insuranceDoc();
        orderDoc();
        productDoc();
        underwritingDoc();
        customerDoc();
        userDoc();
    }

    private void drawHtml(String name, String content) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><head><style>table{width:600px;}td{min-width:80px;padding:0.5em 1em;}</style></head><body>");
        builder.append(content);
        builder.append("</body></html>");
        try {
            Files.write(Paths.get("" + name + "-entity.html"),
                builder.toString().getBytes(),
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dealerDoc() {
        StringBuilder builder = new StringBuilder();
        List<Class<?>> classes = find("app.dealer");
        classes.forEach(clazz -> builder.append(entityDoc(clazz)));
        drawHtml("dealer", builder.toString());
    }

    private void cartDoc() {
        StringBuilder builder = new StringBuilder();
        List<Class<?>> classes = find("cn.kdlins.cart");
        classes.forEach(clazz -> builder.append(entityDoc(clazz)));
        drawHtml("cart", builder.toString());
    }

    private void esbDoc() {
        StringBuilder builder = new StringBuilder();
        List<Class<?>> classes = find("cn.kdlins.esb");
        classes.forEach(clazz -> builder.append(entityDoc(clazz)));
        drawHtml("esb", builder.toString());
    }

    private void insuranceDoc() {
        StringBuilder builder = new StringBuilder();
        List<Class<?>> classes = find("cn.kdlins.insurance");
        classes.forEach(clazz -> builder.append(entityDoc(clazz)));
        drawHtml("insurance", builder.toString());
    }

    private void orderDoc() {
        StringBuilder builder = new StringBuilder();
        List<Class<?>> classes = find("cn.kdlins.order");
        classes.forEach(clazz -> builder.append(entityDoc(clazz)));
        drawHtml("order", builder.toString());
    }

    private void productDoc() {
        StringBuilder builder = new StringBuilder();
        List<Class<?>> classes = find("cn.kdlins.product");
        classes.forEach(clazz -> builder.append(entityDoc(clazz)));
        drawHtml("product", builder.toString());
    }

    private void underwritingDoc() {
        StringBuilder builder = new StringBuilder();
        List<Class<?>> classes = find("cn.kdlins.underwriting");
        classes.forEach(clazz -> builder.append(entityDoc(clazz)));
        drawHtml("underwriting", builder.toString());
    }

    private void customerDoc() {
        StringBuilder builder = new StringBuilder();
        List<Class<?>> classes = find("io.sited.customer");
        classes.forEach(clazz -> builder.append(entityDoc(clazz)));
        drawHtml("customer", builder.toString());
    }

    private void userDoc() {
        StringBuilder builder = new StringBuilder();
        List<Class<?>> classes = find("io.sited.user");
        classes.forEach(clazz -> builder.append(entityDoc(clazz)));
        drawHtml("user", builder.toString());
    }

    private String entityDoc(Class<?> entityClass) {

        if (entityClass.getAnnotation(Entity.class) == null) {
            return "";
        }
        StringBuilder b = new StringBuilder();
        b.append("<h3>").append(entityClass.getAnnotation(Entity.class).name()).append("</h3>");
        b.append("<table style=\"border-collapse: collapse;border: 1px solid #cbcbcb;\">");
        b.append("<tr>")
            .append("<td style='border: 1px solid #cbcbcb;'>").append("字段名").append("</td>")
            .append("<td style='border: 1px solid #cbcbcb;'>").append("类型").append("</td>")
            .append("<td style='border: 1px solid #cbcbcb;'>").append("描述").append("</td>")
            .append("<td style='border: 1px solid #cbcbcb;'>").append("约束").append("</td>")
            .append("</tr>");

        for (Field field : entityClass.getDeclaredFields()) {
            if (field.getAnnotation(io.sited.db.Id.class) != null) {
                b.append("<tr>")
                    .append("<td style='border: 1px solid #cbcbcb;'>").append("id").append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;'>").append(field.getType().getSimpleName()).append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;'>").append("主键").append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;'>").append("UUID").append("</td>")
                    .append("</tr>");
            } else {
                b.append("<tr>")
                    .append("<td style='border: 1px solid #cbcbcb;'>").append(field.getAnnotation(io.sited.db.Field.class).name()).append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;'>").append(field.getType().getSimpleName()).append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;'></td>")
                    .append("<td style='border: 1px solid #cbcbcb;'></td>")
                    .append("</tr>");
            }

        }
        b.append("</table>");
        return b.toString();
    }

}

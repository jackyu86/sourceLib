package app;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.caej.api.esb.KdlinsESBWebService;
import com.caej.api.pay.KdlinsPayWebService;
import com.caej.api.underwritting.KdlinsUnderwritingWebService;
import com.caej.cart.api.CartWebService;
import com.caej.esb.api.ESBRecordWebService;
import com.caej.insurance.api.EnumAccountTypeWebService;
import com.caej.insurance.api.EnumBankTypeWebService;
import com.caej.insurance.api.EnumBeneficiaryTypeWebService;
import com.caej.insurance.api.EnumCertiTypeWebService;
import com.caej.insurance.api.EnumChargeModeTypeWebService;
import com.caej.insurance.api.EnumChargePeriodTypeWebService;
import com.caej.insurance.api.EnumCountWayTypeWebService;
import com.caej.insurance.api.EnumCoveragePeriodTypeWebService;
import com.caej.insurance.api.EnumDeliverTypeWebService;
import com.caej.insurance.api.EnumGenderTypeWebService;
import com.caej.insurance.api.EnumInvoiceDeliverTypeWebService;
import com.caej.insurance.api.EnumLegalBeneficiaryTypeWebService;
import com.caej.insurance.api.EnumMarriageTypeWebService;
import com.caej.insurance.api.EnumOverManageTypeWebService;
import com.caej.insurance.api.EnumOverdueOptionTypeWebService;
import com.caej.insurance.api.EnumPayModeTypeWebService;
import com.caej.insurance.api.EnumPayModeWebService;
import com.caej.insurance.api.EnumPolicyRelationTypeWebService;
import com.caej.insurance.api.EnumYesOrNotTypeWebService;
import com.caej.insurance.api.InsuranceAreaWebService;
import com.caej.insurance.api.InsuranceCategoryWebService;
import com.caej.insurance.api.InsuranceClaimWebService;
import com.caej.insurance.api.InsuranceClauseWebService;
import com.caej.insurance.api.InsuranceCountryWebService;
import com.caej.insurance.api.InsuranceDeclarationWebService;
import com.caej.insurance.api.InsuranceFormFieldWebService;
import com.caej.insurance.api.InsuranceFormGroupWebService;
import com.caej.insurance.api.InsuranceJobTreeWebService;
import com.caej.insurance.api.InsuranceJobWebService;
import com.caej.insurance.api.InsuranceLiabilityGroupWebService;
import com.caej.insurance.api.InsuranceSubjectWebService;
import com.caej.insurance.api.InsuranceVendorWebService;
import com.caej.insurance.api.InsuranceWebService;
import com.caej.order.OrderWebService;
import com.caej.order.PaymentWebService;
import com.caej.order.RefundTrackingWebService;
import com.caej.product.api.ProductFormWebService;
import com.caej.product.api.ProductPriceWebService;
import com.caej.product.api.ProductSearchWebService;
import com.caej.product.api.ProductSerialWebService;
import com.caej.product.api.ProductWebService;
import com.google.common.collect.Lists;

import app.dealer.api.CreditWebService;
import app.dealer.api.DealerProductWebService;
import app.dealer.api.DealerUserWebService;
import app.dealer.api.DealerWebService;
import app.dealer.api.PolicyHolderWebService;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;

/**
 * @author chi
 */
public class InterfaceDoc {
    public static void main(String[] args) {
        InterfaceDoc interfaceDoc = new InterfaceDoc();
        interfaceDoc.doc();
    }

    public void doc() {
        dealerDoc();
        cartDoc();
        esbDoc();
        insuranceDoc();
        orderDoc();
        productDoc();
        kdlinsEsbDoc();
    }

    private void drawHtml(String name, String content) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><head><style>table{width:600px;}td{min-width:80px;padding:0.5em 1em;}</style></head><body>");
        builder.append(content);
        builder.append("</body></html>");
        try {
            Files.write(Paths.get("" + name + ".html"),
                builder.toString().getBytes(),
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dealerDoc() {
        String content = interfaceDoc(CreditWebService.class) +
            interfaceDoc(DealerProductWebService.class) +
            interfaceDoc(DealerUserWebService.class) +
            interfaceDoc(DealerWebService.class) +
            interfaceDoc(PolicyHolderWebService.class);
        drawHtml("dealer", content);
    }

    private void cartDoc() {
        String content = interfaceDoc(CartWebService.class);
        drawHtml("cart", content);
    }

    private void esbDoc() {
        String content = interfaceDoc(ESBRecordWebService.class);
        drawHtml("esb", content);
    }

    private void insuranceDoc() {
        String content = interfaceDoc(EnumAccountTypeWebService.class) +
            interfaceDoc(EnumBankTypeWebService.class) +
            interfaceDoc(EnumBeneficiaryTypeWebService.class) +
            interfaceDoc(EnumCertiTypeWebService.class) +
            interfaceDoc(EnumChargeModeTypeWebService.class) +
            interfaceDoc(EnumChargePeriodTypeWebService.class) +
            interfaceDoc(EnumCountWayTypeWebService.class) +
            interfaceDoc(EnumCoveragePeriodTypeWebService.class) +
            interfaceDoc(EnumDeliverTypeWebService.class) +
            interfaceDoc(EnumGenderTypeWebService.class) +
            interfaceDoc(EnumInvoiceDeliverTypeWebService.class) +
            interfaceDoc(EnumLegalBeneficiaryTypeWebService.class) +
            interfaceDoc(EnumMarriageTypeWebService.class) +
            interfaceDoc(EnumOverdueOptionTypeWebService.class) +
            interfaceDoc(EnumOverManageTypeWebService.class) +
            interfaceDoc(EnumPayModeTypeWebService.class) +
            interfaceDoc(EnumPayModeWebService.class) +
            interfaceDoc(EnumPolicyRelationTypeWebService.class) +
            interfaceDoc(EnumYesOrNotTypeWebService.class) +
            interfaceDoc(InsuranceAreaWebService.class) +
            interfaceDoc(InsuranceCategoryWebService.class) +
            interfaceDoc(InsuranceClaimWebService.class) +
            interfaceDoc(InsuranceClauseWebService.class) +
            interfaceDoc(InsuranceCountryWebService.class) +
            interfaceDoc(InsuranceDeclarationWebService.class) +
            interfaceDoc(InsuranceFormFieldWebService.class) +
            interfaceDoc(InsuranceFormGroupWebService.class) +
            interfaceDoc(InsuranceJobTreeWebService.class) +
            interfaceDoc(InsuranceJobWebService.class) +
            interfaceDoc(InsuranceLiabilityGroupWebService.class) +
            interfaceDoc(InsuranceSubjectWebService.class) +
            interfaceDoc(InsuranceVendorWebService.class) +
            interfaceDoc(InsuranceWebService.class);
        drawHtml("insurance", content);
    }

    private void orderDoc() {
        String content = interfaceDoc(OrderWebService.class) +
            interfaceDoc(PaymentWebService.class) +
            interfaceDoc(RefundTrackingWebService.class);
        drawHtml("order", content);
    }

    private void productDoc() {
        String content = interfaceDoc(ProductFormWebService.class) +
            interfaceDoc(ProductPriceWebService.class) +
            interfaceDoc(ProductSearchWebService.class) +
            interfaceDoc(ProductSerialWebService.class) +
            interfaceDoc(ProductWebService.class);
        drawHtml("product", content);
    }

    private void kdlinsEsbDoc() {
        String content = interfaceDoc(KdlinsESBWebService.class) +
            interfaceDoc(KdlinsPayWebService.class) +
            interfaceDoc(KdlinsUnderwritingWebService.class);
        drawHtml("kdlins", content);
    }

    private String interfaceDoc(Class<?> interfaceClass) {
        StringBuilder b = new StringBuilder();

        for (Method method : interfaceClass.getDeclaredMethods()) {
            b.append(interfaceMethod(method));
        }

        return b.toString();
    }

    private String interfaceMethod(Method method) {
        List<String> rows = Lists.newArrayList();
        StringBuilder b = new StringBuilder();

        b.append("<tr>")
            .append("<td style='border: 1px solid #cbcbcb;width: 80px;'>").append("路径").append("</td>")
            .append("<td style='border: 1px solid #cbcbcb;' colspan=4>").append(method.getDeclaredAnnotation(Path.class).value()).append("</td>")
            .append("</tr>");
        rows.add(b.toString());

        b.setLength(0);
        b.append("<tr>")
            .append("<td style='border: 1px solid #cbcbcb;width: 80px;'>").append("方法").append("</td>")
            .append("<td style='border: 1px solid #cbcbcb;' colspan=4>").append(method(method)).append("</td>")
            .append("</tr>");
        rows.add(b.toString());

        rows.addAll(parameters("参数", method));

        if (method.getReturnType().equals(void.class)) {
            b.setLength(0);
            b.append("<tr>")
                .append("<td style='border: 1px solid #cbcbcb;width: 80px;'>").append("返回").append("</td>")
                .append("<td style='border: 1px solid #cbcbcb;' colspan=4></td>")
                .append("</tr>");
            rows.add(b.toString());
        } else {
            if (isSystemClass(method.getReturnType())) {
                b.setLength(0);
                b.append("<tr>")
                    .append("<td style='border: 1px solid #cbcbcb;width: 80px;'>").append("返回").append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;' colspan=4>").append(method.getReturnType().getSimpleName()).append("</td>")
                    .append("</tr>");
                rows.add(b.toString());
            } else {
                rows.addAll(bean("返回", method.getReturnType(), 2));
            }
        }

        b.setLength(0);
        b.append("<h3>").append(method.getDeclaringClass().getSimpleName() + "." + method.getName()).append("</h3>");
        b.append("<table style=\"border-collapse: collapse;border: 1px solid #cbcbcb;\">");

        rows.forEach(b::append);
        b.append("</table>");

        return b.toString();
    }

    private String method(Method method) {
        if (method.isAnnotationPresent(GET.class)) {
            return "GET";
        } else if (method.isAnnotationPresent(POST.class)) {
            return "POST";
        } else if (method.isAnnotationPresent(PUT.class)) {
            return "PUT";
        } else {
            return "DELETE";
        }
    }

    private List<String> parameters(String name, Method method) {
        StringBuilder b = new StringBuilder();
        Parameter[] parameters = method.getParameters();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (isSystemClass(parameter.getType())) {
                b.setLength(0);
                b.append("<tr>")
                    .append("<td style='border: 1px solid #cbcbcb;'>").append(parameter.getName()).append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;'>").append(parameter.getType().getSimpleName()).append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;width: 100%;' colspan=2>").append("</td>")
                    .append("</tr>");
                list.add(b.toString());
            } else {
                list.addAll(bean(parameter.getName(), parameter.getType(), 1));
            }
        }
        if (!list.isEmpty()) {
            list.set(0, list.get(0).replace("<tr>", "<tr><td style='border: 1px solid #cbcbcb;' rowspan=" + list.size() + ">" + name + "</td>"));
        }
        return list;
    }

    private boolean isSystemClass(Class type) {
        return type.getName().startsWith("java.") || type.getName().startsWith("javax.") || type.getName().startsWith("org.");
    }

    private List<String> bean(String name, Class<?> type, int rowspan) {
        StringBuilder b = new StringBuilder();
        Field[] fields = type.getDeclaredFields();
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < fields.length; i++) {
            b.setLength(0);
            Field field = fields[i];
            if (i == 0) {
                b.append("<tr>")
                    .append("<td style='border: 1px solid #cbcbcb;' rowspan=").append(fields.length).append(">").append(name).append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;'>").append(field.getName()).append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;'>").append(field.getType().getSimpleName()).append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;width: 100%;' colspan=").append(rowspan).append("></td>")
                    .append("</tr>");

            } else {
                b.append("<tr>")
                    .append("<td style='border: 1px solid #cbcbcb;'>").append(field.getName()).append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;'>").append(field.getType().getSimpleName()).append("</td>")
                    .append("<td style='border: 1px solid #cbcbcb;width: 100%;' colspan=").append(rowspan).append("></td>")
                    .append("</tr>");

            }
            list.add(b.toString());
        }
        return list;
    }

}

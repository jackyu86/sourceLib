package io.sited.template.impl.element.writer;

import io.sited.StandardException;
import io.sited.db.FindView;
import io.sited.template.Attribute;
import io.sited.template.Element;
import io.sited.template.ElementWriter;
import io.sited.template.TemplateContext;

import java.io.IOException;
import java.io.Writer;

/**
 * @author chi
 */
public class PaginationElementWriter implements ElementWriter {
    @Override
    public void output(Element element, TemplateContext context, Writer writer) throws IOException {
        Attribute items = element.attributes().get("items");
        if (items == null) {
            throw new StandardException("missing items");
        }
        FindView<?> findView = (FindView<?>) items.eval(context.bindings);
        Attribute displayAttribute = element.attributes().get("display");
        int display = displayAttribute == null ? 10 : Integer.valueOf(displayAttribute.value());
        Attribute prefixAttribute = element.attributes().get("prefix");
        String prefix = prefixAttribute == null ? "/" : prefixAttribute.value();

        int limit = findView.limit;
        int current = findView.page;
        long total = findView.total % limit == 0 ? findView.total / limit : findView.total / limit + 1;

        int start = current - display / 2 > 0 ? current - display / 2 : 1;
        int end = current + display / 2 < total ? current + display / 2 : (int) total;

        StringBuilder b = new StringBuilder();

        if (start > 1) {
            b.append("<li class=\"prev\"><a href=\"").append(prefix).append(current - 1)
                .append("/\">«</a></li>");
        }

        for (int i = start; i <= end; i++) {
            if (i == current) {
                b.append("<li class=\"active\">");
            } else {
                b.append("<li>");
            }
            b.append("<a href=\"").append(prefix).append(i)
                .append("/\">")
                .append(i)
                .append("</a></li>");
        }
        if (end < total) {
            b.append("<li class=\"next\"><a href=\"").append(prefix).append(current + 1)
                .append("/\">»</a></li>");
        }
        writer.write(b.toString());
    }
}

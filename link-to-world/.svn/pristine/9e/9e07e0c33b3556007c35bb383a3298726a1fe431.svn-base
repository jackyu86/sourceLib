package io.sited.template.impl.element.writer;

import com.google.common.base.Strings;
import io.sited.template.Attribute;
import io.sited.template.Element;
import io.sited.template.TemplateContext;

import java.io.IOException;
import java.io.Writer;

/**
 * @author chi
 */
public class TitleElementWriterImpl extends StaticElementWriterImpl {
    @Override
    public void output(Element element, TemplateContext context, Writer writer) throws IOException {
        if (isContextTitleEnabled(element, context)) {
            writer.write("<title");
            for (Attribute attribute : element.attributes().values()) {
                attribute.output(context, writer);
            }
            writer.write(">");

            writer.write(context.title);
            writer.write("</title>");
        } else {
            super.output(element, context, writer);
        }
    }

    private boolean isContextTitleEnabled(Element element, TemplateContext templateContext) {
        return element.children().isEmpty() || !Strings.isNullOrEmpty(templateContext.title) && !"j:text".equals(element.children().get(0).node().name);
    }
}

package io.sited.template.impl.element.writer;

import io.sited.template.Attribute;
import io.sited.template.Element;
import io.sited.template.TemplateContext;

import java.io.IOException;
import java.io.Writer;

/**
 * @author chi
 */
public class HeadElementWriterImpl extends StaticElementWriterImpl {
    @Override
    public void output(Element element, TemplateContext context, Writer writer) throws IOException {
        if (context.include) {
            return;
        }
        if (context.cssFiles.isEmpty()) {
            super.output(element, context, writer);
        } else {
            writer.write("<");
            writer.write(element.node().name);

            for (Attribute attribute : element.attributes().values()) {
                attribute.output(context, writer);
            }

            writer.write(">");

            for (Element child : element.children()) {
                child.output(context, writer);
            }

            for (String cssFile : context.cssFiles) {
                writer.write(cssLink(cssFile));
            }

            writer.write("</");
            writer.write(element.node().name);
            writer.write(">");
        }
    }

    private String cssLink(String cssFile) {
        return "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssFile + "\">";
    }
}

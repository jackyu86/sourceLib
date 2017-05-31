package io.sited.template.impl.element.writer;

import com.google.common.collect.Sets;
import io.sited.template.Attribute;
import io.sited.template.Element;
import io.sited.template.ElementWriter;
import io.sited.template.TemplateContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * @author chi
 */
public class StaticElementWriterImpl implements ElementWriter {
    private static final Set<String> SELF_CLOSED_TAGS = Sets.newHashSet("br", "area", "base", "col", "embed", "hr", "img", "input", "keygen", "link", "meta", "param", "source", "track", "wbr");
    private final Logger logger = LoggerFactory.getLogger(StaticElementWriterImpl.class);

    @Override
    public void output(Element element, TemplateContext context, Writer writer) throws IOException {
        writer.write("<");
        writer.write(element.node().name);

        for (Attribute attribute : element.attributes().values()) {
            attribute.output(context, writer);
        }

        if (isSelfClosed(element.node().name)) {
            if (!element.children().isEmpty()) {
                logger.warn("invalid self closed element, name={}", element.node().name);
            }
            writer.write("/>");
        } else {
            writer.write(">");

            for (Element child : element.children()) {
                child.output(context, writer);
            }

            writer.write("</");
            writer.write(element.node().name);
            writer.write(">");
        }
    }

    private boolean isSelfClosed(String name) {
        return SELF_CLOSED_TAGS.contains(name);
    }
}

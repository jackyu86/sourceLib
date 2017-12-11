package io.sited.web.impl.service;

import io.sited.Site;
import io.sited.http.Request;
import io.sited.i18n.I18nConfig;
import io.sited.i18n.Message;
import io.sited.template.Attribute;
import io.sited.template.Element;
import io.sited.template.ElementWriter;
import io.sited.template.TemplateContext;

import javax.inject.Inject;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import java.util.Optional;

/**
 * @author chi
 */
public class MessageElementWriterImpl implements ElementWriter {
    @Inject
    I18nConfig i18nConfig;
    @Inject
    Site site;

    @Override
    public void output(Element element, TemplateContext context, Writer writer) throws IOException {
        Attribute attribute = element.attributes().get("message");
        Optional<Message> message = i18nConfig.message(attribute.value(), locale(context));
        if (message.isPresent()) {
            writer.write(message.get().get());
        } else {
            writer.write(element.attributes().get("text").value());
        }
    }

    private Locale locale(TemplateContext context) {
        Request request = (Request) context.bindings.get("_request");
        return request == null ? site.locale() : request.locale();
    }
}

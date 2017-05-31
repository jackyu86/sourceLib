package com.caej.site.page.service;

import app.dealer.api.dealer.DealerResponse;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import io.sited.http.Request;
import io.sited.http.exception.UnauthenticatedException;
import io.sited.template.Element;
import io.sited.template.ElementWriter;
import io.sited.template.TemplateContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author chi
 */
public class DealerElementWriter implements ElementWriter {
    private final Logger logger = LoggerFactory.getLogger(DealerElementWriter.class);

    @Override
    public void output(Element element, TemplateContext context, Writer writer) throws IOException {
        Map<String, List<String>> configMap = Maps.newHashMap();
        Splitter.on(";").splitToList(element.attributes().get("dealer").value()).forEach(config -> {
            List<String> values = Splitter.on(":").splitToList(config);
            configMap.put(values.get(0), Splitter.on(',').splitToList(values.get(1)));
        });
        if (canOutput(configMap, context)) {
            for (Element child : element.children()) {
                child.output(context, writer);
            }
        }
    }

    private boolean canOutput(Map<String, List<String>> configMap, TemplateContext context) {
        try {
            DealerResponse dealer = request(context.bindings).require(DealerResponse.class, null);
            if (configMap.containsKey("level")) {
                Set<String> levels = configMap.get("level").stream().collect(Collectors.toSet());
                return dealer != null && levels.contains(dealer.level.name());
            }
            if (configMap.containsKey("status")) {
                Set<String> statuses = configMap.get("status").stream().collect(Collectors.toSet());
                return dealer != null && statuses.contains(dealer.status.name());
            }
        } catch (UnauthenticatedException e) {
            logger.debug(e.getMessage());
        }
        return true;
    }

    private Request request(Map<String, Object> bindings) {
        return (Request) bindings.get("_request");
    }
}

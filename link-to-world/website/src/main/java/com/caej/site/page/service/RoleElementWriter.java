package com.caej.site.page.service;

import com.google.common.base.Splitter;
import io.sited.http.Request;
import io.sited.template.Element;
import io.sited.template.ElementWriter;
import io.sited.template.TemplateContext;
import io.sited.user.web.User;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class RoleElementWriter implements ElementWriter {
    @Override
    public void output(Element element, TemplateContext context, Writer writer) throws IOException {
        List<String> roles = Splitter.on(',').splitToList(element.attributes().get("role").value());
        Request request = request(context.bindings);
        User user = request.require(User.class, null);
        if (user != null && hasRole(user, roles)) {
            for (Element child : element.children()) {
                child.output(context, writer);
            }
        }
    }

    private boolean hasRole(User user, List<String> roles) {
        for (String role : roles) {
            if (user.hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    private Request request(Map<String, Object> bindings) {
        return (Request) bindings.get("_request");
    }
}

package io.sited.http.impl;

import java.util.Map;

/**
 * @author chi
 */
public class TemplateBody {
    public final String templatePath;
    public final Map<String, Object> context;

    public TemplateBody(String templatePath, Map<String, Object> context) {
        this.templatePath = templatePath;
        this.context = context;
    }
}

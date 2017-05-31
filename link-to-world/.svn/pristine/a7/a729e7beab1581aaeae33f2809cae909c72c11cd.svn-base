package io.sited.admin.impl.web.ajax;

import com.google.common.collect.Lists;
import io.sited.admin.impl.web.ajax.template.TemplateAJAXResponse;
import io.sited.http.GET;
import io.sited.http.Path;
import io.sited.template.TemplateConfig;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chi
 */
public class TemplateAJAXController {
    @Inject
    TemplateConfig templateConfig;

    @Path("/admin/ajax/template")
    @GET
    public List<TemplateAJAXResponse> find() {
        return Lists.newArrayList(templateConfig.repository()).stream().map(source -> {
            TemplateAJAXResponse response = new TemplateAJAXResponse();
            response.path = source.path();
            response.templateName = source.path();
            return response;
        }).collect(Collectors.toList());
    }
}

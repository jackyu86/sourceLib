package io.sited.admin.impl.web.ajax;

import io.sited.Site;
import io.sited.http.GET;
import io.sited.http.Path;

import javax.inject.Inject;

/**
 * @author chi
 */
public class SiteAJAXController {
    @Inject
    Site site;

    @Path("/admin/ajax/site")
    @GET
    public SiteAjaxResponse site() {
        SiteAjaxResponse siteAjaxResponse = new SiteAjaxResponse();
        siteAjaxResponse.name = site.name();
        siteAjaxResponse.host = site.host();
//        siteAjaxResponse.dir = site.dir().getAbsolutePath();
        siteAjaxResponse.description = site.description();
        return siteAjaxResponse;
    }

    public static class SiteAjaxResponse {
        public String name;
        public String description;
        public String host;
        public String dir;
    }
}

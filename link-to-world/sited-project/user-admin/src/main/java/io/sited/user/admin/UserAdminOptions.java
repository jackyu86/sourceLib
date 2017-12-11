package io.sited.user.admin;

import com.google.common.collect.Lists;
import io.sited.admin.ConsoleOptions;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author chi
 */
public class UserAdminOptions {
    public List<Pattern> excludeURLs = Lists.newArrayList(
        Pattern.compile("/admin/login"),
        Pattern.compile("/admin/static/.*"),
        Pattern.compile("/admin/ajax/user/authenticate"));

    public Boolean defaultAdminEnabled = true;
    public ConsoleOptions console = new ConsoleOptions();
}

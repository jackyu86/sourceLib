package io.sited.admin;

import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author chi
 */
public class AdminOptions {
    public Boolean enabled = true;
    public Boolean cacheEnabled = true;
    public List<File> dirs = Lists.newArrayList();
    public List<Pattern> excludeURLs = Lists.newArrayList();
    public ConsoleOptions console = new ConsoleOptions();
}

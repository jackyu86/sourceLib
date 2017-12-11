package io.sited.file.service;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author chi
 */
public class DirectoryParser {
    private final String path;

    public DirectoryParser(String path) {
        this.path = path;
    }

    public List<String> parents() {
        List<String> parents = Lists.newArrayList();
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            b.append(c);
            if (c == '/' && i != path.length() - 1) {
                parents.add(b.toString());
            }
        }
        return parents;
    }
}

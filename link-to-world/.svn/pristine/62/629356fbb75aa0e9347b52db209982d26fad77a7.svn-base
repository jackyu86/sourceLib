package io.sited.file.admin.service;

import java.util.function.Function;

/**
 * @author chi
 */
public class DefaultPathSuggesterImpl implements Function<String, String> {
    @Override
    public String apply(String name) {
        StringBuilder b = new StringBuilder();
        boolean skip = false;

        String input = name.trim();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isAlphabetic(c)) {
                b.append(c);
                skip = false;
            } else {
                if (!skip) {
                    b.append('-');
                    skip = true;
                }
            }
        }
        return b.toString();
    }
}

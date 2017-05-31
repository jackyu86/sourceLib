package io.sited.file.admin;

/**
 * @author chi
 */
public interface PathSuggester {
    SuggestedPath suggest(String name);

    class SuggestedPath {
        public String path;
    }
}

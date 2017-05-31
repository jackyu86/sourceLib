package io.sited.template;

import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class Node {
    public String id;
    public String name;
    public Map<String, String> attributes;
    public List<Node> children;

    public String text() {
        StringBuilder b = new StringBuilder();
        if (children != null) {
            children.stream().filter(child -> "TEXT".equals(child.name)).forEach(child -> {
                b.append(child.attributes.get("text"));
            });
        }
        return b.toString();
    }
}

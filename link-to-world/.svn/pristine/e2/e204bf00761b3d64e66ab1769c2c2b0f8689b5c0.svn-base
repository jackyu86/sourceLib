package io.sited.template.impl.node.processor;

import com.google.common.collect.Maps;
import io.sited.template.Node;
import io.sited.template.impl.node.NodeImpl;
import io.sited.template.impl.node.NodeProcessor;

import java.util.UUID;

/**
 * @author chi
 */
public class HtmlNodeProcessorImpl implements NodeProcessor {
    @Override
    public int priority() {
        return 101;
    }

    @Override
    public Node process(Node node) {
        String jHtml = "j:html";

        if (node.attributes.containsKey(jHtml)) {
            NodeImpl htmlNode = new NodeImpl(node);
            htmlNode.name = jHtml;
            htmlNode.id = UUID.randomUUID().toString();
            htmlNode.attributes = Maps.newHashMap();
            htmlNode.attributes.put(jHtml, node.attributes.get(jHtml));
            node.attributes.remove(jHtml);
            node.children.clear();
            node.children.add(htmlNode);
        }
        return node;
    }
}

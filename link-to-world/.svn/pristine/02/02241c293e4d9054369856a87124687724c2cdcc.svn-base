package io.sited.template.impl.node.processor;

import com.google.common.collect.Maps;
import io.sited.template.Node;
import io.sited.template.impl.node.NodeImpl;
import io.sited.template.impl.node.NodeProcessor;

import java.util.UUID;

/**
 * @author chi
 */
public class TextNodeProcessorImpl implements NodeProcessor {
    @Override
    public int priority() {
        return 100;
    }

    @Override
    public Node process(Node node) {
        String jText = "j:text";

        if (node.attributes.containsKey(jText)) {
            NodeImpl textNode = new NodeImpl(node);
            textNode.name = jText;
            textNode.id = UUID.randomUUID().toString();
            textNode.attributes = Maps.newHashMap();
            textNode.attributes.put(jText, node.attributes.get(jText));
            node.attributes.remove(jText);
            node.children.clear();
            node.children.add(textNode);
        }
        return node;
    }
}

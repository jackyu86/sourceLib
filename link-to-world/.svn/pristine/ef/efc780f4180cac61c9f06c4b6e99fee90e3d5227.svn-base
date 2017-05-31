package io.sited.template.impl.node.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.template.Node;
import io.sited.template.impl.node.NodeImpl;
import io.sited.template.impl.node.NodeProcessor;

import java.util.UUID;

/**
 * @author chi
 */
public class ForNodeProcessorImpl implements NodeProcessor {
    @Override
    public int priority() {
        return -200;
    }

    @Override
    public Node process(Node node) {
        String jFor = "j:for";
        if (node.attributes.containsKey(jFor)) {
            NodeImpl forNode = new NodeImpl(node);
            forNode.name = jFor;
            forNode.id = UUID.randomUUID().toString();
            forNode.children = Lists.newArrayList();
            forNode.children.add(node);
            forNode.attributes = Maps.newHashMap();
            forNode.attributes.put(jFor, node.attributes.get(jFor));
            node.attributes.remove(jFor);
            return forNode;
        }
        return node;
    }
}

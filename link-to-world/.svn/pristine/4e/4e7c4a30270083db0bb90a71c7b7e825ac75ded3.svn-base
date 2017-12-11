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
public class IfNodeProcessorImpl implements NodeProcessor {
    @Override
    public int priority() {
        return -100;
    }

    @Override
    public Node process(Node node) {
        String jIf = "j:if";
        if (node.attributes.containsKey(jIf)) {
            NodeImpl ifNode = new NodeImpl(node);
            ifNode.name = jIf;
            ifNode.id = UUID.randomUUID().toString();
            ifNode.attributes = Maps.newHashMap();
            ifNode.attributes.put(jIf, node.attributes.get(jIf));
            ifNode.children = Lists.newArrayList();
            ifNode.children.add(node);
            node.attributes.remove(jIf);

            return ifNode;
        }
        return node;
    }
}

package com.caej.site.page.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.template.Node;
import io.sited.template.impl.node.NodeImpl;
import io.sited.template.impl.node.NodeProcessor;

import java.util.UUID;

/**
 * @author chi
 */
public class DealerNodeProcessor implements NodeProcessor {
    @Override
    public Node process(Node node) {
        String jDealer = "j:dealer";
        if (node.attributes.containsKey(jDealer)) {
            NodeImpl forNode = new NodeImpl(node);
            forNode.name = jDealer;
            forNode.id = UUID.randomUUID().toString();
            forNode.children = Lists.newArrayList();
            forNode.children.add(node);
            forNode.attributes = Maps.newHashMap();
            forNode.attributes.put(jDealer, node.attributes.get(jDealer));
            node.attributes.remove(jDealer);
            return forNode;
        }
        return node;
    }
}

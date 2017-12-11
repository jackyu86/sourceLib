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
public class RoleNodeProcessor implements NodeProcessor {
    @Override
    public Node process(Node node) {
        String jRole = "j:role";
        if (node.attributes.containsKey(jRole)) {
            NodeImpl forNode = new NodeImpl(node);
            forNode.name = jRole;
            forNode.id = UUID.randomUUID().toString();
            forNode.children = Lists.newArrayList();
            forNode.children.add(node);
            forNode.attributes = Maps.newHashMap();
            forNode.attributes.put(jRole, node.attributes.get(jRole));
            node.attributes.remove(jRole);
            return forNode;
        }
        return node;
    }
}

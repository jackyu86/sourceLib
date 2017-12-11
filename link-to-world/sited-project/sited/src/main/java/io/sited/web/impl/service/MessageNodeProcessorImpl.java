package io.sited.web.impl.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.template.Node;
import io.sited.template.impl.node.NodeImpl;
import io.sited.template.impl.node.NodeProcessor;

import java.util.UUID;

/**
 * @author chi
 */
public class MessageNodeProcessorImpl implements NodeProcessor {
    @Override
    public int priority() {
        return -100;
    }

    @Override
    public Node process(Node node) {
        String jMsg = "j:msg";
        if (node.attributes.containsKey(jMsg)) {
            NodeImpl messageNode = new NodeImpl(node);
            messageNode.name = jMsg;
            messageNode.id = UUID.randomUUID().toString();
            messageNode.children = Lists.newArrayList();
            messageNode.attributes = Maps.newHashMap();
            messageNode.attributes.put("message", node.attributes.get(jMsg));
            messageNode.attributes.put("text", node.text());
            node.attributes.remove(jMsg);
            node.children = Lists.newArrayList(messageNode);
        }
        return node;
    }
}

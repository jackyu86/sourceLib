package io.sited.util;

import com.google.common.collect.Sets;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * @author chi
 */
public class GraphTest {
    @Test
    public void test() {
        MutableGraph<Integer> graph = GraphBuilder.directed().allowsSelfLoops(false)
            .build();

        graph.putEdge(3, 4);
        graph.putEdge(1, 2);
        graph.putEdge(2, 3);

        Set<Integer> visited = Sets.newLinkedHashSet();
        while (graph.nodes().size() != visited.size()) {
            for (Integer node : graph.nodes()) {
                Set<Integer> dependencies = graph.predecessors(node);

                boolean sanctified = true;
                for (Integer dependency : dependencies) {
                    if (!visited.contains(dependency)) {
                        sanctified = false;
                    }
                }
                if (sanctified) {
                    visited.add(node);
                }
            }
        }

        Assert.assertNotNull(graph);
    }
}
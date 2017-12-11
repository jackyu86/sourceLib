package io.sited.http.impl;


import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.StandardException;
import io.sited.http.HttpMethod;
import io.sited.http.Interceptor;
import io.sited.http.Route;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
public class Router {
    private final NodeRef root = new NodeRef(new RoutePath.Node("/"));

    public Router add(String route, HandlerRef handlerRef) {
        RoutePath routePath = new RoutePath(handlerRef.httpMethod, route);
        NodeRef current = root;
        for (int i = 1; i < routePath.nodes.size(); i++) {
            RoutePath.Node node = routePath.nodes.get(i);
            current = current.findOrCreate(node);
        }
        current.handlers.put(handlerRef.httpMethod, handlerRef);
        current.routePath = routePath;
        return this;
    }

    public Router add(String route, Interceptor interceptor) {
        RoutePath routePath = new RoutePath(null, route);
        NodeRef current = root;
        for (int i = 1; i < routePath.nodes.size(); i++) {
            RoutePath.Node node = routePath.nodes.get(i);
            current = current.findOrCreate(node);
        }
        current.interceptors.add(interceptor);
        current.routePath = routePath;
        return this;
    }

    public Optional<Route> find(HttpMethod method, String path) {
        RoutePath p = new RoutePath(method, path);
        LinkedList<NodeRef> stack = Lists.newLinkedList();

        root.find(p, stack);
        if (stack.isEmpty()) {
            return Optional.empty();
        }

        NodeRef nodeRef = stack.getLast();
        if (nodeRef.handlers.containsKey(method)) {
            HandlerRef handlerRef = nodeRef.handlers.get(method);
            List<String> variableValues = Lists.newArrayList();
            List<Interceptor> interceptors = Lists.newArrayList();
            for (int i = 0; i < stack.size(); i++) {
                NodeRef next = stack.get(i);
                if (!next.interceptors.isEmpty()) {
                    interceptors.addAll(next.interceptors);
                }
                if (next.isVariable()) {
                    variableValues.add(p.nodes.get(i).path);
                } else if (next.isWildcard()) {
                    break;
                }
            }
            Map<String, String> pathParams = Maps.newHashMapWithExpectedSize(variableValues.size());
            for (int i = 0; i < stack.getLast().routePath.variableNames.size(); i++) {
                String value = variableValues.get(i);
                try {
                    pathParams.put(stack.getLast().routePath.variableNames.get(i), URLDecoder.decode(value, Charsets.UTF_8.name()));
                } catch (UnsupportedEncodingException e) {
                    throw new StandardException(e);
                }
            }
            return Optional.of(new Route(handlerRef, pathParams, interceptors));
        }
        return Optional.empty();
    }

    public List<HandlerRef> handlerRefs() {
        List<HandlerRef> routes = Lists.newArrayList();
        routes(root, routes);
        return routes;
    }

    private void routes(NodeRef node, List<HandlerRef> routes) {
        node.handlers.forEach((httpMethod, handlerRef) -> routes.add(handlerRef));
        node.children.forEach(child -> routes(child, routes));
    }

    private static class NodeRef {
        RoutePath.Node node;
        List<NodeRef> children = Lists.newArrayList();
        boolean variable;
        boolean wildcard;
        RoutePath routePath;
        Map<HttpMethod, HandlerRef> handlers = Maps.newHashMap();
        List<Interceptor> interceptors = Lists.newArrayList();

        NodeRef(RoutePath.Node node) {
            this.node = node;
            variable = node instanceof RoutePath.PathVariable;
            wildcard = node instanceof RoutePath.Wildcard;
        }

        boolean isVariable() {
            return variable;
        }

        boolean isWildcard() {
            return wildcard;
        }

        NodeRef findOrCreate(RoutePath.Node node) {
            for (NodeRef child : children) {
                if (child.node.path.equals(node.path)) {
                    return child;
                }
            }
            NodeRef nodeRef = new NodeRef(node);
            int index = children.size();
            for (int i = 0; i < children.size(); i++) {
                if (node.priority() > children.get(i).node.priority()) {
                    index = i;
                    break;
                }
            }
            children.add(index, nodeRef);
            return nodeRef;
        }

        NodeRef find(RoutePath routePath, Deque<NodeRef> stack) {
            int index = routePath.index;
            if (node.matches(routePath.current().path)) {
                stack.add(this);
                if (!routePath.hasNext() || this.isWildcard()) {
                    return this;
                }

                routePath.index++;
                for (NodeRef child : children) {
                    NodeRef nodeRef = child.find(routePath, stack);
                    if (nodeRef != null && nodeRef.handlers.containsKey(routePath.method)) {
                        return nodeRef;
                    } else if (nodeRef != null) {
                        stack.pollLast();
                    }
                }
                stack.pollLast();
            }
            routePath.index = index;
            return null;
        }
    }
}

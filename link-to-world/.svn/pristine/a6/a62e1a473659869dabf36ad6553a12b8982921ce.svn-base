package io.sited.http.impl;


import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.sited.StandardException;
import io.sited.http.HttpMethod;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chi
 */
public class RoutePath {
    private static final Pattern PATH_VARIABLE_PATTERN = Pattern.compile("(\\w+)(\\(.*\\))?");
    public final HttpMethod method;
    public final String path;
    public final List<String> variableNames = Lists.newArrayList();
    final List<Node> nodes = Lists.newArrayList();

    public int index = 0;

    public RoutePath(HttpMethod method, String path) {
        Preconditions.checkArgument(path.startsWith("/"), "path must start with /, path=%s", path);

        this.method = method;
        this.path = path;
        nodes.add(new Node("/"));
        StringBuilder b = new StringBuilder();

        int state = 0;
        for (int i = 1; i < path.length(); i++) {
            char c = path.charAt(i);
            if (c == ':') {
                state = 2;
            } else if (c == '*') {
                Preconditions.checkArgument(i == path.length() - 1, "route path only support tailing wildcard, path={}", path);
                nodes.add(new Wildcard());
            } else if (c == '/') {
                if (state == 2) {
                    nodes.add(pathVariable(b.toString()));
                    b.delete(0, b.length());
                } else {
                    nodes.add(new Node(b.toString()));
                    b.delete(0, b.length());
                }
                nodes.add(new Node("/"));
                state = 0;
            } else if (i == path.length() - 1) {
                b.append(c);
                if (state == 2) {
                    nodes.add(pathVariable(b.toString()));
                } else {
                    nodes.add(new Node(b.toString()));
                }
            } else {
                b.append(c);
                if (state == 0) {
                    state = 1;
                }
            }
        }
    }

    public String fill(Map<String, String> pathParams) {
        StringBuilder b = new StringBuilder();

        for (Node node : nodes) {
            if (node instanceof PathVariable) {
                PathVariable variable = (PathVariable) node;
                String value = pathParams.get(variable.path);
                if (value == null) {
                    throw new StandardException("missing path variable, path={}, variable={}", path, variable.path);
                }
                b.append(value);
            } else if (node instanceof Wildcard) {
                throw new StandardException("path doesn't support fill wildcard path, pattern={}", path);
            } else {
                b.append(node.path);
            }
        }
        return b.toString();
    }

    public boolean hasNext() {
        return index < nodes.size() - 1;
    }

    public Node next() {
        if (index < nodes.size()) {
            return nodes.get(++index);
        }

        return null;
    }

    public Node prev() {
        if (index > 0) {
            return nodes.get(--index);
        }
        return null;
    }

    public boolean hasPrev() {
        return index > 0;
    }

    Node lastNode() {
        return nodes.get(nodes.size() - 1);
    }

    Node current() {
        return nodes.get(index);
    }

    final PathVariable pathVariable(String path) {
        Matcher matcher = PATH_VARIABLE_PATTERN.matcher(path);
        if (!matcher.matches()) {
            throw new Error("invalid path variable " + path);
        }
        variableNames.add(matcher.group(1));
        if (matcher.group(2) != null) {
            return new PathVariable(matcher.group(1), matcher.group(2));
        } else {
            return new PathVariable(matcher.group(1), ".*");
        }
    }

    public static class Node {
        public final String path;

        Node(String path) {
            this.path = path;
        }

        boolean matches(String path) {
            return this.path.equalsIgnoreCase(path);
        }

        public int priority() {
            return Integer.MAX_VALUE;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                .add("path", path)
                .toString();
        }
    }

    static class PathVariable extends Node {
        final Pattern pattern;

        PathVariable(String path, String regex) {
            super(path);
            pattern = Pattern.compile(regex);
        }

        boolean matches(String path) {
            return pattern.matcher(path).matches();
        }

        public int priority() {
            return path.length();
        }
    }

    static class Wildcard extends Node {
        Wildcard() {
            super("*");
        }

        boolean matches(String path) {
            return true;
        }

        public int priority() {
            return 0;
        }
    }
}

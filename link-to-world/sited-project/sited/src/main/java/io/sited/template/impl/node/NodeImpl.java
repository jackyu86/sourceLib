package io.sited.template.impl.node;

import com.google.common.base.MoreObjects;
import io.sited.template.Node;

/**
 * @author chi
 */
public class NodeImpl extends Node {
    public final Integer row;
    public final Integer column;

    public NodeImpl(Integer row, Integer column) {
        this.row = row;
        this.column = column;
    }

    public NodeImpl(Node node) {
        this(((NodeImpl) node).row, ((NodeImpl) node).column);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(name)
            .add("row", row)
            .add("column", column).toString();
    }
}

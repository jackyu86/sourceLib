package io.sited.template;

/**
 * @author chi
 */
public interface Filter {
    Object filter(Object value, Object[] params);
}

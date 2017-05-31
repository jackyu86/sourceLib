package io.sited.db;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * @author chi
 */
public class FindView<T> implements Iterable<T> {
    public Integer page;
    public Integer limit;
    public Long total;
    public List<T> items = Lists.newArrayList();

    public static <T, K> FindView<K> map(FindView<T> findView, Function<T, K> converter) {
        FindView<K> results = new FindView<>();
        results.page = findView.page;
        results.limit = findView.limit;
        results.total = findView.total;
        results.items = Lists.newArrayList();
        findView.items.forEach(t -> {
            K value = converter.apply(t);
            results.items.add(value);
        });
        return results;
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }
}

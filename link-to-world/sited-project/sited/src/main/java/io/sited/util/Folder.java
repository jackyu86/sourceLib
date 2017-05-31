package io.sited.util;

import com.google.common.collect.Lists;
import io.sited.StandardException;

import java.io.File;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.function.Predicate;

/**
 * @author chi
 */
public class Folder {
    private final File dir;

    public Folder(File dir) {
        if (!dir.isDirectory()) {
            throw new StandardException("not a dir, {}", dir);
        }
        this.dir = dir;
    }

    public Iterator<File> iterator(Predicate<File> predicate) {
        Deque<File> stack = Lists.newLinkedList();
        stack.add(dir);

        return new Iterator<File>() {
            File current;

            @Override
            public boolean hasNext() {
                while (!stack.isEmpty() && current == null) {
                    File file = stack.pollLast();
                    if (predicate.test(file)) {
                        current = file;
                    } else {
                        File[] children = file.listFiles();
                        if (children != null && children.length != 0) {
                            for (File f : children) {
                                stack.addLast(f);
                            }
                        }
                    }
                }
                return current != null;
            }

            @Override
            public File next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                File next = current;
                current = null;
                return next;
            }
        };
    }

    public void delete() {
        Stack<File> stack = new Stack<>();
        stack.push(dir);
        while (!stack.isEmpty()) {
            File current = stack.pop();
            File[] children = current.listFiles();
            if (children != null && children.length != 0) {
                stack.add(current);
                for (File file : children) {
                    if (file.isDirectory()) {
                        stack.add(file);
                    } else {
                        file.delete();
                    }
                }
            } else {
                current.delete();
            }
        }
    }
}

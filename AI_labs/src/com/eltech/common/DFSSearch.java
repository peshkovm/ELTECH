package com.eltech.common;

        import java.util.ArrayDeque;
        import java.util.Collections;

public class DFSSearch<S, A> extends GraphSearch<S, A> {
    public DFSSearch() {
        super(Collections.asLifoQueue(new ArrayDeque<Node<S, A>>()));
    }
}

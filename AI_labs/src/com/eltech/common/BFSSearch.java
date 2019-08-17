package com.eltech.common;

import java.util.ArrayDeque;

public class BFSSearch<S, A> extends GraphSearch<S, A> {
    public BFSSearch() {
        super(new ArrayDeque<Node<S, A>>());
    }
}

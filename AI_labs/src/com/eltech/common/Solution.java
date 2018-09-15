package com.eltech.common;

import java.util.ArrayList;

public class Solution<S,A> extends ArrayList<Node<S,A>> {
    public Solution(Node<S,A> node) {
        this.add(node);
    }
    public Solution() {
        super();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

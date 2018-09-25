package com.eltech.common;

import java.util.ArrayList;
import java.util.Collections;

public class Solution<S, A> extends ArrayList<Node<S, A>> {
    int capacity;
    int time;

    public Solution(Node<S, A> node, int max_capacity) {
        this.add(node);
        this.capacity = max_capacity;
    }

    public Solution() {
        super();
    }

    @Override
    public String toString() {
        if (!this.isEmpty()) {
            Node<S, A> node = this.remove(0);

            while (node != null) {
                this.add(node);
                node = node.getParent();
            }

            Collections.reverse(this);

            return super.toString();
        }

        return super.toString();
    }

    public String memory() {
        return String.valueOf(capacity);
    }
}

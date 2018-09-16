package com.eltech.common;

import java.util.ArrayList;
import java.util.Collections;

public class Solution<S, A> extends ArrayList<Node<S, A>> {
    public Solution(Node<S, A> node) {
        this.add(node);
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
}

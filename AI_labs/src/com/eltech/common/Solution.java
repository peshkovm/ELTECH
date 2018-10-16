package com.eltech.common;

import java.util.ArrayList;
import java.util.Collections;

public class Solution<S, A> {
    int capacity = 0;
    public Node<S, A> node = null;
    int depth = 0;
    int time;

    public Solution(Node<S, A> node, int max_capacity, int time) {
        this.node = node;
        this.capacity = max_capacity;
        this.time = time;
    }

    public Solution() {
    }

    public int getPathCost() {
        return node != null ? node.getPath_Cost() : 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        while (node != null) {
            str.insert(0, node + "\n");
            node = node.getParent();
            depth++;
        }
        return str.toString();
    }

    public String memory() {
        return String.valueOf(capacity);
    }

    public String depth() {
        return String.valueOf(depth);
    }

    public String time() {
        return String.valueOf(time);
    }

    public boolean isExists() {
        return node != null;
    }
}

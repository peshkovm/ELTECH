package com.eltech.lab2;

import com.eltech.common.GraphSearch;
import com.eltech.common.Node;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStar<S, A> extends GraphSearch<S, A> {
    public AStar(Comparator<? super Node<S, A>> comp) {
        super(new PriorityQueue<Node<S, A>>(comp));
    }
}

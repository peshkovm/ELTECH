package com.eltech.lab1;

import java.util.Collection;
import java.util.Queue;

public class Game8 extends GraphSearch<Integer[][], String> {

    private static class Matrice{

    }

    public Game8(Queue<Node<Integer[][], String>> fringe) {
        super(fringe);
    }

    @Override
    protected Node<Integer[][], String> Solution(Node<Integer[][], String> node) {
        return node;
    }

    @Override
    protected boolean Goal_Test(Integer[][] state) {
        if (state.equals(new Integer[][]{
                {1, 2, 3},
                {null, 4, 5},
                {6, 7, 8}
        }))
            return true;
        return false;
    }

    @Override
    protected Collection<Node<Integer[][], String>> Expand(Node<Integer[][], String> node) {

    }
}

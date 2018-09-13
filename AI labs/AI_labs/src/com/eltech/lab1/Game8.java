package com.eltech.lab1;

import java.util.*;

class Matrix {
    Matrix(Integer[][] mas, int x, int y) {
        this.mas = mas;
        this.x = x;
        this.y = y;
    }

    Integer[][] mas;
    int x;
    int y;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Matrix) {
            Matrix temp = (Matrix) obj;
            if (Arrays.deepEquals(mas, temp.mas))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(mas);
    }
}

public class Game8 extends GraphSearch<Matrix, String> {

    List<Integer> a = new ArrayList<Integer>();

    public Game8(Queue<Node<Matrix, String>> fringe) {
        super(fringe);
    }

    @Override
    protected Node<Matrix, String> Solution(Node<Matrix, String> node) {
        return node;
    }

    @Override
    protected boolean Goal_Test(Matrix state) {
        if (state.equals(new Matrix(new Integer[][]{{1, 2, 3}, {null, 4, 5}, {6, 7, 8}}, 0, 1)))
            return true;
        return false;
    }

    @Override
    protected Collection<Node<Matrix, String>> Expand(Node<Matrix, String> node) {

    }
}

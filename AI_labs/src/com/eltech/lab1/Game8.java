package com.eltech.lab1;

import com.eltech.common.GraphSearch;
import com.eltech.common.Node;

import java.util.*;

class Matrix {

    private int[] mas;
    private int x;

    public Matrix(int[] mas, int x) {
        this.mas = mas;
        this.x = x;
    }

    public int[] getMas() {
        return mas;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Matrix) {
            Matrix temp = (Matrix) obj;
            if (Arrays.equals(mas,temp.mas))
                return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mas);
    }

    @Override
    public String toString() {
        return Arrays.toString(mas);
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
        if (state.equals(new Matrix(new Matrix(new Integer[][]{{1, null, 2}, {3, 4, 5}, {6, 7, 8}}, 0, 1))))
            return true;
        return false;
    }

    @Override
    protected Collection<Node<Matrix, String>> Expand(Node<Matrix, String> node) {
        List<Node<Matrix, String>> ls = new ArrayList<Node<Matrix, String>>();
        int x = node.getState().getX();
        int y = node.getState().getY();
        Matrix temp;

        if (x != 0) {
            temp = new Matrix(node.getState());
            temp.swap(-1, 0);
            temp.setX(temp.getX() - 1);
            ls.add(new Node<Matrix, String>(temp, "left", node, node.getPath_Cost() + 1));
        }
        if (x != 2) {
            temp = new Matrix(node.getState());
            temp.swap(1, 0);
            temp.setX(temp.getX() + 1);
            ls.add(new Node<Matrix, String>(temp, "right", node, node.getPath_Cost() + 1));
        }
        if (y != 0) {
            temp = new Matrix(node.getState());
            temp.swap(0, -1);
            temp.setY(temp.getY() - 1);
            ls.add(new Node<Matrix, String>(temp, "up", node, node.getPath_Cost() + 1));
        }
        if (y != 2) {
            temp = new Matrix(node.getState());
            temp.swap(0, 1);
            temp.setY(temp.getY() + 1);
            ls.add(new Node<Matrix, String>(temp, "down", node, node.getPath_Cost() + 1));
        }

        return ls;
    }

    public static void main(String[] args) {
        Game8 game = new Game8(new ArrayDeque<>());
        Node<Matrix, String> node = game.search(new Matrix(new Integer[][]{{null, 1, 2}, {3, 4, 5}, {7, 6, 8}}, 0, 0));
        if (node != null)
            System.out.println(node.getState());
    }
}

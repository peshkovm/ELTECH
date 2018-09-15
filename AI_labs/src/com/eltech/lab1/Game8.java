package com.eltech.lab1;

import com.eltech.common.GraphSearch;
import com.eltech.common.Node;
import com.eltech.common.Problem;
import com.eltech.common.Solution;

import java.util.*;

class Matrix {

    private int[] mas;
    private int x;

    public Matrix(int[] mas, int x) {
        this.mas = mas;
        this.x = x;
    }

    public Matrix(Matrix matrix) {
        this.mas = Arrays.copyOf(matrix.mas, matrix.mas.length);
        x = matrix.getX();
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
            if (Arrays.equals(mas, temp.mas))
                return true;
        }
        return false;
    }

    public void swap(int x) {
        int temp = mas[this.x];
        mas[this.x] = mas[x];
        mas[x] = temp;
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

    public Game8(ArrayDeque<Node<Matrix, String>> fringe) {
        super(fringe);
    }

    public static void main(String[] args) {
        Game8 game = new Game8(new ArrayDeque<Node<Matrix, String>>());
        Solution<Matrix, String> solution = game.search(new ProblemGame8(new Matrix(new int[]{1, 8, 2, 0, 4, 3, 7, 6, 5}, 3)));
        //new EightPuzzleDemo();

        System.out.println(solution);
    }
}

class ProblemGame8 extends Problem<Matrix, String> {

    ProblemGame8(Matrix state) {
        super(state);
    }

    @Override
    public List<Pair<String, Matrix>> Successor_Fn(Matrix state) {
        List<Pair<String, Matrix>> ls = new ArrayList<Pair<String, Matrix>>();

        int x = state.getX();

        if (x != 0 && x != 1 && x != 2) {
            Matrix matrix = new Matrix(state);
            matrix.swap(x - 3);
            matrix.setX(x - 3);
            ls.add(new Pair<String, Matrix>("up", matrix));
        }

        if (x != 0 && x != 3 && x != 6) {
            Matrix matrix = new Matrix(state);
            matrix.swap(x - 1);
            matrix.setX(x - 1);
            ls.add(new Pair<String, Matrix>("left", matrix));
        }

        if (x != 6 && x != 7 && x != 8) {
            Matrix matrix = new Matrix(state);
            matrix.swap(x + 3);
            matrix.setX(x + 3);
            ls.add(new Pair<String, Matrix>("down", matrix));
        }

        if (x != 2 && x != 5 && x != 8) {
            Matrix matrix = new Matrix(state);
            matrix.swap(x + 1);
            matrix.setX(x + 1);
            ls.add(new Pair<String, Matrix>("right", matrix));
        }

        return ls;
    }

    @Override
    public boolean Goal_Test(Matrix state) {
        return state.equals(new Matrix(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0}, 8));
    }

    @Override
    public int Step_Cost(Node<Matrix, String> node, String action, Matrix new_state) {
        return node.getPath_Cost() + 1;
    }
}
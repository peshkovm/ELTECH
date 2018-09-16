package com.eltech.lab1;

import com.eltech.common.*;

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
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < mas.length; i++) {
            if (i % 3 == 0) {
                str.append("\n");
            }
            str.append(mas[i] + " ");
        }
        return str.toString();
    }
}

public class Game8 {

    public static void main(String[] args) {
        BFSSearch<Matrix, String> obj = new BFSSearch<>();
        long time = System.currentTimeMillis();
        Solution<Matrix, String> solution = obj.search(new ProblemGame8(new Matrix(new int[]{0, 4, 3, 6, 2, 1, 7, 5, 8}, 0)));
        long timeRes = System.currentTimeMillis();

        System.out.println(solution);

        System.out.println(timeRes - time);
    }
}

class ProblemGame8 extends Problem<Matrix, String> {

    Matrix goalState = new Matrix(new int[]{1, 2, 3, 4, 0, 5, 6, 7, 8}, 4);

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
        return state.equals(goalState);
    }

    @Override
    public int Step_Cost(Node<Matrix, String> node, String action, Matrix new_state) {
        return 1;
    }
}
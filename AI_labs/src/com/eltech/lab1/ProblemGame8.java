package com.eltech.lab1;

import com.eltech.common.Node;
import com.eltech.common.Problem;

import java.util.ArrayList;
import java.util.List;

public class ProblemGame8 extends Problem<Matrix, String> {

    public static final Matrix goalState = new Matrix(new int[]{1, 2, 3, 4, 0, 5, 6, 7, 8});

    public ProblemGame8(Matrix state) {
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
            ls.add(new Pair<String, Matrix>("\u2191", matrix));
        }

        if (x != 0 && x != 3 && x != 6) {
            Matrix matrix = new Matrix(state);
            matrix.swap(x - 1);
            matrix.setX(x - 1);
            ls.add(new Pair<String, Matrix>("\u2190", matrix));
        }

        if (x != 6 && x != 7 && x != 8) {
            Matrix matrix = new Matrix(state);
            matrix.swap(x + 3);
            matrix.setX(x + 3);
            ls.add(new Pair<String, Matrix>("\u2193", matrix));
        }

        if (x != 2 && x != 5 && x != 8) {
            Matrix matrix = new Matrix(state);
            matrix.swap(x + 1);
            matrix.setX(x + 1);
            ls.add(new Pair<String, Matrix>("\u2192", matrix));
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
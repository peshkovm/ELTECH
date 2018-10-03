package com.eltech.lab2;

import com.eltech.common.Node;
import com.eltech.lab1.Matrix;
import com.eltech.lab1.ProblemGame8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class Euristics {
    private static int f1(Node<Matrix, String> node) {
        int f = 0;
        Matrix matrix = node.getState();
        for (Iterator<Integer> i = matrix.iterator(), j = ProblemGame8.goalState.iterator(); i.hasNext(); ) {
            if (!i.next().equals(j.next())) f++;
        }
        return f + node.getPath_Cost();
    }

    private static int f2(Node<Matrix, String> node) {
        int f = 0;
        Matrix matrix = node.getState();
        int pos;
        int[] mas = matrix.getMas();
        for (int i = 0; i < mas.length; i++) {
            pos = search(ProblemGame8.goalState.getMas(), mas[i]);
            f += Math.abs(i / 3 - pos / 3) + Math.abs(i % 3 - pos % 3);
        }
        return f + node.getPath_Cost();
    }

    static Comparator<Node<Matrix, String>> CountOfTiles() {
        return Comparator.comparingInt(Euristics::f1);
    }

    static Comparator<Node<Matrix, String>> ManhattanDistance() {
        return Comparator.comparingInt(Euristics::f2);
    }

    private static int search(int[] mas, int key) {
        int res = -1;
        for (int i = 0; i < mas.length; i++) {
            if (mas[i] == key) res = i;
        }
        return res;
    }
}

package com.eltech.lab1;

import java.util.Arrays;
import java.util.Iterator;

public class Matrix implements Iterable<Integer> {

    private int[] mas;
    private int x;

    public Matrix(int[] mas) {
        this.mas = mas;

        for (int i = 0; i < mas.length; i++) {
            if (mas[i] == 0)
                this.x = i;
        }
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
            str.append(mas[i]).append(" ");
        }
        return str.toString();
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

            private int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < mas.length;
            }

            @Override
            public Integer next() {
                return mas[cursor++];
            }
        };
    }
}

package com.eltech.lab1;

public interface Node<S,A> {
    S State();
    A Action();
    Node<S,A> Parent();
    int Path_Cost();
    int Depth();
}

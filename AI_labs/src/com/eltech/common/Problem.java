package com.eltech.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Problem<S, A> {

    static public class Pair<A, S> {

        private A action;
        private S state;

        public Pair(A action, S state) {
            this.action = action;
            this.state = state;
        }

        public A getAction() {
            return action;
        }

        public void setAction(A action) {
            this.action = action;
        }

        public S getState() {
            return state;
        }

        public void setState(S state) {
            this.state = state;
        }
    }

    public Problem(S state) {
        InitialState = state;
    }

    public S getInitialState() {
        return InitialState;
    }

    private S InitialState;

    abstract public List<Pair<A, S>> Successor_Fn(S state);

    abstract public boolean Goal_Test(S state);

    abstract public int Step_Cost(Node<S, A> node, A action, S state);

}

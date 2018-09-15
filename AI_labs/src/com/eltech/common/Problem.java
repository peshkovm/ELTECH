package com.eltech.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Problem<S, A> {

    static private class Pair<A, S> {

        private A action;
        private S state;

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

    Problem (S state) {
        InitialState=state;
    }

    public S getInitialState() {
        return InitialState;
    }

    private S InitialState;

    abstract List<Pair<A, S>> Successor_Fn(S state);

    abstract boolean Goal_Test(S state);

    abstract int Step_Cost(S state, A action, S new_state);

}

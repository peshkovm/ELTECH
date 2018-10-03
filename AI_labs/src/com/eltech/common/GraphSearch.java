package com.eltech.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public abstract class GraphSearch<S, A> {
    protected Set<S> closed = new HashSet<S>();
    protected Queue<Node<S, A>> fringe;
    int max = 0;
    int timeComplexity = 0;

    public GraphSearch(Queue<Node<S, A>> fringe) {
        this.fringe = fringe;
    }

    public Solution<S, A> search(Problem<S, A> problem) {
        fringe.add(new Node<S, A>(problem.getInitialState()));
        while (true) {
            max = fringe.size() > max ? fringe.size() : max;
            if (fringe.isEmpty())
                return new Solution<S, A>(); // empty
            Node<S, A> node = fringe.poll();
            timeComplexity++;
            if (problem.Goal_Test(node.getState()))
                return new Solution<S, A>(node, max, timeComplexity);
            if (!closed.contains(node.getState())) {
                closed.add(node.getState());
                fringe.addAll(Expand(node, problem));
            }
        }
    }

    public void searhStep(Problem<S, A> problem) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);

        fringe.add(new Node<S, A>(problem.getInitialState()));
        while (true) {
            System.out.println("fringe=" + fringe + "\n");
            if (fringe.isEmpty()) {
                System.out.println("Error");
                return;
            }
            Node<S, A> node = fringe.poll();
            System.out.println("node=" + node.getState() + "\n");
            if (problem.Goal_Test(node.getState())) {
                System.out.println("Finish" + node.getState());
                return;
            }
            System.out.println("closed= " + closed + "\n");
            if (!closed.contains(node.getState())) {
                List<S> list = new ArrayList<S>();
                closed.add(node.getState());
                for (Node<S, A> a : Expand(node, problem)) {
                    if (!closed.contains(a.getState())) {
                        fringe.add(a);
                        list.add(a.getState());
                    }
                }
                System.out.println("expanded=" + list + "\n");
            }

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            reader.read();
        }
    }

    protected Collection<Node<S, A>> Expand(Node<S, A> node, Problem<S, A> problem) {
        List<Node<S, A>> successors = new ArrayList<Node<S, A>>();

        for (Problem.Pair<A, S> pair : problem.Successor_Fn(node.getState())) {
            Node<S, A> s = new Node<S, A>(pair.getState(), pair.getAction(), node, node.getPath_Cost() + problem.Step_Cost(node, pair.getAction(), pair.getState()));
            successors.add(s);
        }

        /*problem.Successor_Fn(node.getState()).forEach(pair -> {
            Node<S, A> s = new Node<S, A>(pair.getState(), pair.getAction(), node, node.getPath_Cost() + problem.Step_Cost(node, pair.getAction(), pair.getState()));
            successors.add(s);
        });*/

        return successors;
    }
}
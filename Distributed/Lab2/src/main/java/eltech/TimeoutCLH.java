package eltech;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TimeoutCLH implements Lock {
    private static final Node AVAILABLE = new Node();
    private final AtomicReference<Node> tail;
    private final ThreadLocal<Node> myNode;

    private static class Node {
        private volatile Node pred;
    }

    public TimeoutCLH() {
        tail = new AtomicReference<Node>(null);
        myNode = ThreadLocal.withInitial(Node::new);
    }

    public void lock() {
        throw new UnsupportedOperationException("no lock");
    }

    public void lockInterruptibly() {
        throw new UnsupportedOperationException("no lockInterruptibly");
    }

    public boolean tryLock() {
        throw new UnsupportedOperationException("no tryLock");
    }

    public boolean tryLock(long time, TimeUnit unit) {
        long startTime = System.currentTimeMillis();
        long patience = TimeUnit.MILLISECONDS.convert(time, unit);
        Node qnode = new Node();
        myNode.set(qnode);
        qnode.pred = null;
        Node myPred = tail.getAndSet(qnode);
        if (myPred == null || myPred.pred == AVAILABLE) {
            return true;
        }
        while (System.currentTimeMillis() - startTime < patience) {
            Node predPred = myPred.pred;
            if (predPred == AVAILABLE) {
                return true;
            } else if (predPred != null) {
                myPred = predPred;
            }
        }
        if (!tail.compareAndSet(qnode, myPred))
            qnode.pred = myPred;
        return false;
    }

    public void unlock() {
        Node qnode = myNode.get();
        if (!tail.compareAndSet(qnode, null))
            qnode.pred = AVAILABLE;
    }

    public Condition newCondition() {
        throw new UnsupportedOperationException("no newCondition");
    }
}
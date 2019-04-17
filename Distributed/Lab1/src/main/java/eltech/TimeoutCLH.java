package eltech;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TimeoutCLH implements Lock {
    private static final Node AVAILABLE = new Node(); //signifies free lock
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
        Node myPred = tail.getAndSet(qnode); //swap with tail
        if (myPred == null || myPred.pred == AVAILABLE) { //if predecessor absent or released, we are done
            return true;
        }
        while (System.currentTimeMillis() - startTime < patience) {
            Node predPred = myPred.pred; //spin on predecessor's prev field
            if (predPred == AVAILABLE) { //predecesor released lock
                return true;
            } else if (predPred != null) { //predecessor aborted
                myPred = predPred;
            }
        }
        if (!tail.compareAndSet(qnode, myPred)) //if CAS succeeds: no successor, tail just set to my pred, simply return false
            qnode.pred = myPred; // if CAS  fails, I do have a successor. Tell it about myPred
        return false;
    }

    public void unlock() {
        Node qnode = myNode.get();
        if (!tail.compareAndSet(qnode, null)) //If CAS failed: successor exists, notify it can enter
            qnode.pred = AVAILABLE; //CAS successful: set tail to null, no clean up since no successor waiting
    }

    public Condition newCondition() {
        throw new UnsupportedOperationException("no newCondition");
    }
}
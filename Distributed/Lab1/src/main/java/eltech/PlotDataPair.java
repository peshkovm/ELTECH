package eltech;

import java.util.List;

class PlotDataPair {
    private String lockName;
    private List<TimeExecThreadCountPair> pairs;

    public PlotDataPair(String lockName, List<TimeExecThreadCountPair> pairs) {
        this.lockName = lockName;
        this.pairs = pairs;
    }

    public String getLockName() {
        return lockName;
    }

    public List<TimeExecThreadCountPair> getPairs() {
        return pairs;
    }

    static class TimeExecThreadCountPair {
        private long execTime;
        private int countOfThreads;

        public TimeExecThreadCountPair(long execTime, int countOfThreads) {
            this.execTime = execTime;
            this.countOfThreads = countOfThreads;
        }

        public long getExecTime() {
            return execTime;
        }

        public int getCountOfThreads() {
            return countOfThreads;
        }
    }
}
package ar.edu.itba.ss.utils;

public class Metrics {

    private Integer totalComparisons =0;
    private Long start;
    private Long stop;

    public Metrics() {
        this.start = null;
        this.stop = null;
    }

    public int getTotalComparisons() {
        return this.totalComparisons;
    }

    public void increaseComparisons() {
        this.totalComparisons++;
    }

    public void startTime() {
        if(this.start != null) {
            return;
        }
        this.start = System.currentTimeMillis();
    }

    public void stopTime() {
        if(this.start == null || this.stop != null) {
            return;
        }
        this.stop = System.currentTimeMillis();
    }

    public long getTime() {
        if(this.start == null || this.stop == null) {
            return 0;
        }
        return this.stop - this.start;
    }
}

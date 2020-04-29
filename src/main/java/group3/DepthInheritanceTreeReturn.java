package group3;

public class DepthInheritanceTreeReturn extends MetricReturn {
    private int maxDepth;


    public DepthInheritanceTreeReturn() {
        this.maxDepth = 0;
    }

    public int getMaxDepth () {
        return this.maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }
}

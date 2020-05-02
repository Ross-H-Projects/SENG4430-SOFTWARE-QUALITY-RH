package group3;

public class LengthOfIdentifiersReturn extends MetricReturn {
    private double averageLengthOfIdentifiers;

    public LengthOfIdentifiersReturn(){
        this.averageLengthOfIdentifiers = 0;
    }

    public double getAverageLengthOfIdentifiers() {
        return averageLengthOfIdentifiers;
    }

    public void setAverageLengthOfIdentifiers(double averageLengthOfIdentifiers) {
        this.averageLengthOfIdentifiers = averageLengthOfIdentifiers;
    }
}

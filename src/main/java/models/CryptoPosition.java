package models;

public class CryptoPosition {
    private String symbol;
    private double lastPosition;
    private double currentPosition;
    private double performance;

    public CryptoPosition(String symbol, double lastPosition, double currentPosition, double performance) {
        this.symbol = symbol;
        this.lastPosition = lastPosition;
        this.currentPosition = currentPosition;
        this.performance = performance;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getCurrentPosition() {
        return currentPosition;
    }

    public double getPerformance() {
        return performance;
    }
}

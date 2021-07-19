package models;

public class CryptoPrice {
    private String priceUsd;
    private String time;
    private String date;

    public CryptoPrice(String priceUsd, String time, String date) {
        this.priceUsd = priceUsd;
        this.time = time;
        this.date = date;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

}

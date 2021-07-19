import models.Asset;
import models.CryptoPosition;
import models.CryptoPrices;

class CryptoThread implements Runnable {

    private String[] line;
    private Asset asset;
    private CoinCap coinCap;

    public CryptoThread(String[] line, Asset lineAsset, CoinCap coinCap) {
        this.line = line;
        this.asset = lineAsset;
        this.coinCap = coinCap;
    }

    @Override
    public void run() {

        CryptoPrices dataPrices = null;
        CoinCap coinCap = new CoinCap();
        dataPrices = coinCap.getCryptoPrice(asset);

        double priceRequest = Double.parseDouble(dataPrices.getData().get(0).getPriceUsd());
        double quantity = Double.parseDouble(line[1]);

        double currentPosition = priceRequest * quantity;
        double lastPosition = Double.parseDouble(line[2]) * quantity;

        double performance = currentPosition / lastPosition;

        CryptoPosition cp = new CryptoPosition(asset.getSymbol(), lastPosition, currentPosition, performance);

        Process.walletPositions.add(cp);

    }
}

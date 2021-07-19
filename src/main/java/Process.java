import models.Asset;
import models.CryptoPosition;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class Process {

    static List<CryptoPosition> walletPositions = Collections.synchronizedList(new ArrayList());

    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private CoinCap coinCap;

    public Process(CoinCap coinCap) {
        this.coinCap = coinCap;
    }

    public String processWallet(List<String> content) throws IOException, InterruptedException {

        Map<String, Asset> assetMap = coinCap.getAssetMap();

        Date now = new Date();
        System.out.println("Now is " + sdf.format(now));

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

        final List<Future<CryptoThread>> futures = new ArrayList<>();

        for (int i = 1; i < content.size(); i++) {
            String[] line = content.get(i).split(",");
            String lineSymbol = line[0];
            Asset lineAsset = assetMap.get(lineSymbol);
            futures.add((Future<CryptoThread>) executor.submit(new CryptoThread(line, lineAsset, coinCap)));
        }

        for (Future<CryptoThread> f : futures) {
            try {
                f.get(10, TimeUnit.SECONDS);
            } catch (TimeoutException | InterruptedException | ExecutionException e) {

            }
        }

        while (executor.getCompletedTaskCount() < futures.size()) {
            System.out.println(executor.getCompletedTaskCount());
        }
        executor.shutdown();

        double totalPosition = 0;
        String bestAssetSymbol = "";
        double bestPerformance = 0;
        String worstAssetSymbol = "";
        double worstPerformance = Double.MAX_VALUE;

        for (int i = 0; i < walletPositions.size(); i++) {

            CryptoPosition cryptoPosition = walletPositions.get(i);

            if (cryptoPosition.getPerformance() > bestPerformance) {
                bestPerformance = cryptoPosition.getPerformance();
                bestAssetSymbol = cryptoPosition.getSymbol();
            }

            if (cryptoPosition.getPerformance() < worstPerformance) {
                worstPerformance = cryptoPosition.getPerformance();
                worstAssetSymbol = cryptoPosition.getSymbol();
            }

            totalPosition += walletPositions.get(i).getCurrentPosition();

        }

        BigDecimal bdTotalPosition = new BigDecimal(Double.toString(totalPosition)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal bdBestPerformance = new BigDecimal(Double.toString(bestPerformance)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal bdWorstPerformance = new BigDecimal(Double.toString(worstPerformance)).setScale(2, RoundingMode.HALF_UP);

        String response =
                "total=".concat(bdTotalPosition.toString())
                        .concat(",best_asset=").concat(bestAssetSymbol)
                        .concat(",best_performance=").concat(bdBestPerformance.toString())
                        .concat(",worst_asset=").concat(worstAssetSymbol)
                        .concat(",worst_performance=").concat(bdWorstPerformance.toString());

        return response;
    }
}

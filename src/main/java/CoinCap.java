import com.google.gson.Gson;
import models.Asset;
import models.Assets;
import models.CryptoPrices;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CoinCap {

    private final static String baseUrl = "https://api.coincap.io/v2/assets";
    private final static String historyPathParam = "/history";
    private final static String intervalQueryParam = "interval=d1";
    private final static String startQueryParam = "start=1617753600000";
    private final static String endQueryParam = "end=1617753601000";
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public Map<String, Asset> getAssetMap() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest assetsRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .GET()
                .build();

        HttpResponse<String> assetsResponse = client.send(assetsRequest,
                HttpResponse.BodyHandlers.ofString());

        Assets dataAssets = new Gson().fromJson(assetsResponse.body(), Assets.class);
        Map<String, Asset> assetMap = new HashMap<>();

        for (int i = 0; i < dataAssets.getData().size(); i++) {
            assetMap.put(dataAssets.getData().get(i).getSymbol(), dataAssets.getData().get(i));
        }

        return assetMap;

    }

    public CryptoPrices getCryptoPrice(Asset asset) {
        HttpClient client = HttpClient.newHttpClient();

        String uriString = baseUrl
                .concat("/").concat(asset.getId().toLowerCase())
                .concat(historyPathParam)
                .concat("?").concat(intervalQueryParam)
                .concat("&").concat(startQueryParam)
                .concat("&").concat(endQueryParam);

        HttpRequest cryptoRequest = HttpRequest.newBuilder()
                .uri(URI.create(uriString))
                .GET()
                .build();

        HttpResponse<String> cryptoResponse = null;

        try {
            cryptoResponse = client.send(cryptoRequest,
                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Date now = new Date();
        System.out.println("Submitted request " + asset.getSymbol() + " at " + sdf.format(now));

        CryptoPrices dataPrices = new Gson().fromJson(cryptoResponse.body(), CryptoPrices.class);
        return dataPrices;
    }

}

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessTest {

    List<String> csvContent = new ArrayList<>();

    @Before
    public void fillCsv() {

        csvContent.add("symbol,quantity,price");
        csvContent.add("BTC,0.12345,37870.5058");
        csvContent.add("ETH,4.89532,2004.9774");

    }

    @Test
    public void happyFlowTest() throws IOException, InterruptedException {

        String expectedResponse = "total=16984.62,best_asset=BTC,best_performance=1.51,worst_asset=ETH,worst_performance=1.01";
        CoinCap coinCap = new CoinCap();
        Process processor = new Process(coinCap);
        String response = processor.processWallet(csvContent);
        assertEquals(expectedResponse, response);

        System.out.println();

    }

}

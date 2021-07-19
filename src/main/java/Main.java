import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        FileReader fr = new FileReader();
        Path filePath = Paths.get("src", "main", "resources", "crypto-wallet.csv");

        List<String> content = fr.readCsv(filePath);

        CoinCap coinCap = new CoinCap();

        Process processor = new Process(coinCap);

        String response = processor.processWallet(content);

        System.out.println(response);

    }
}


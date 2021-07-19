import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileReaderTest {

    @Test
    public void readCsvFileTest() throws IOException {

        FileReader fr = new FileReader();
        Path filePath = Paths.get("src", "test", "resources", "crypto-wallet.csv");
        List<String> content = fr.readCsv(filePath);

        assertEquals(content.size(), 3);
        assertEquals(content.get(0), "symbol,quantity,price");
        assertEquals(content.get(1), "BTC,0.12345,37870.5058");
        assertEquals(content.get(2), "ETH,4.89532,2004.9774");

    }

}

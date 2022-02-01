import io.InputService;
import xml.XmlUtils;

import java.util.Map;

/* TODO:
- Należy napisać parser pliku z kursami walut (dopuszczalne jest użycie bibliotek do parsowania XMLa) i klasę kalkulatora.
- Kalkulator powinien przyjmować kwotę w EUR i docelową walutę, zwracać kwotę w docelowej walucie.
 */
public class Calculator {
   private static final String FILE_NAME = "eurofxref-daily.xml";

    public static void main(String[] args) {
        Map<String, Double> currencies = XmlUtils.readDataFromXml(FILE_NAME);
        InputService inputService = new InputService();
        inputService.getDataFromUser(currencies);
    }

}

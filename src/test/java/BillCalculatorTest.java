import com.phonecompany.billing.BillCalculator;
import com.phonecompany.billing.TelephoneBillCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BillCalculatorTest {

    @Test
    @DisplayName("Calculte two line log")
    void testTwoLineLog() {
        String log = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57\n" +
                "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00\n";
        TelephoneBillCalculator calculator = new BillCalculator();
        System.out.println(calculator.calculate(log));
    }
}

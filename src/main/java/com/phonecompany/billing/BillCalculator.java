package com.phonecompany.billing;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BillCalculator implements TelephoneBillCalculator {


    @Override
    public BigDecimal calculate(String phoneLog) {
        List<PhoneLogItem> phoneLogList = readLog(phoneLog);
        String freeNumber = calculateMostUsedNumber(phoneLogList);
        return calculateBill(phoneLogList, freeNumber);
    }

    private BigDecimal calculateBill(List<PhoneLogItem> phoneLogList, String freeNumber) {
        return phoneLogList.stream()
                .filter(it -> it.getPhoneNumber() != freeNumber)
                .map(it -> calculateCallPrice(it))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateCallPrice(PhoneLogItem phoneCall) {
        BigDecimal callPrice = BigDecimal.ZERO;
        BigDecimal minutePrice;
        if (phoneCall.getCallStart().toLocalTime().isAfter(LocalTime.of(8, 0)) && phoneCall.getCallStart().toLocalTime().isBefore(LocalTime.of(16, 0))) {
            minutePrice = BigDecimal.ONE;
        } else {
            minutePrice = BigDecimal.valueOf(0.5);
        }
        // TODO calculate price of one call
        callPrice = minutePrice.multiply(BigDecimal.valueOf(ChronoUnit.MINUTES.between(phoneCall.getCallStart(), phoneCall.getCallEnd())));
        return callPrice;
    }

    private String calculateMostUsedNumber(List<PhoneLogItem> phoneLoglist) {
        return phoneLoglist.stream()
                .map(PhoneLogItem::getPhoneNumber)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(it -> it.getKey())
                .get();
    }

    private List<PhoneLogItem> readLog(String phoneLog) {
        return Arrays.stream(phoneLog.split("[\n\r]+"))
                .map(it -> PhoneLogItem.parse(it))
                .collect(Collectors.toList());
    }

}

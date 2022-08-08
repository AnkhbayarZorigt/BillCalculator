package com.phonecompany.billing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneLogItem {
    private String phoneNumber;
    private LocalDateTime callStart;
    private LocalDateTime callEnd;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static PhoneLogItem parse(@NonNull String logLine) throws IllegalArgumentException {
       String[] lineItems = logLine.split(",");
       if (lineItems.length != 3) {
           throw new IllegalArgumentException("Line is invalid: " + logLine);
       }
       PhoneLogItem phoneLogItem = new PhoneLogItem();
       phoneLogItem.phoneNumber = lineItems[0];
       phoneLogItem.callStart = LocalDateTime.parse(lineItems[1], dateTimeFormatter);
       phoneLogItem.callEnd = LocalDateTime.parse(lineItems[2], dateTimeFormatter);
       return phoneLogItem;
    }
}

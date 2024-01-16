package br.com.gurumatch.customerorders.helpers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(localDateTime);
    }

    public static LocalDateTime stringToLocalDateTime(String date, String format) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format));
    }

    public static BigDecimal applyDiscount(BigDecimal amount, BigDecimal percentageDiscount) {
        if (amount == null || percentageDiscount == null) {
            throw new IllegalArgumentException("Valores não podem ser nulos");
        }

        if (percentageDiscount.compareTo(BigDecimal.ZERO) < 0 || percentageDiscount.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new IllegalArgumentException("Percentual de desconto deve estar entre 0 e 100");
        }

        BigDecimal discount = amount.multiply(percentageDiscount)
                .divide(BigDecimal.valueOf(100), MathContext.DECIMAL32);

        return amount.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }


}

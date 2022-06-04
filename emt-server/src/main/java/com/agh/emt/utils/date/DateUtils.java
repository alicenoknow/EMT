package com.agh.emt.utils.date;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    // parse date from YYYY-MM-DD format
    public static LocalDateTime parseDate(String dateStr) throws DateTimeParseException {
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

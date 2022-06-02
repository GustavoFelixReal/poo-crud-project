package util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.util.converter.LocalDateStringConverter;

public class DateConverter {
  private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public static Date localDateToDate(LocalDate date) {
    return java.sql.Date.valueOf(date);
  }

  public static LocalDateStringConverter localDateConverter() {
    return new LocalDateStringConverter(formatter, null);
  }

  public static boolean isValid(String dateStr) {
    try {
      formatter.parse(dateStr);
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }
}

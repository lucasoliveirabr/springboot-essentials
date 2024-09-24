package academy.devdojo.springboot.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateCreator {

  public static String createValidDate() {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
  }

}
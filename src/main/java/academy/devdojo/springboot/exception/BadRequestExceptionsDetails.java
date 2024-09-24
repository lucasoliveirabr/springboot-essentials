package academy.devdojo.springboot.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class BadRequestExceptionsDetails extends ExceptionDetails {
  private String title;
  private int status;
  private String details;
  private String developerMessage;
  private LocalDateTime timestamp;
}
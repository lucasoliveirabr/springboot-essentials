package academy.devdojo.springboot.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimePostRequestBody {
  @NotBlank(message = "The anime name cannot be empty, null or blank space.")
  @Schema(
      description = "This is the Anime's name.",
      example = "Tensei Shittara Slime Datta Ken",
      requiredMode = Schema.RequiredMode.REQUIRED
  )
  private String name;
}
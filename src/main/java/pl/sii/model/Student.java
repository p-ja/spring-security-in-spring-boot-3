package pl.sii.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record Student(
        Integer id,

        @NotNull @NotEmpty
        String name
) {
}

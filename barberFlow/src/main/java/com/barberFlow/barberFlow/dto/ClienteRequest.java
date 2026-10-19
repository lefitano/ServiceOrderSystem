package  com.barberFlow.barberFlow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequest(@NotBlank @Size(max = 150) String nome, @NotBlank @Email @Size(max = 150) String email, @NotBlank @Size(min = 6, max = 72) String senha, @NotBlank @Size(max = 50) String telefone) {

    public ClienteRequest {
        email = email == null ? null : email.trim().toLowerCase();
    }
}

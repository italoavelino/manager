package br.com.uaifood.manager.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterDTO {

    @NotBlank(message = "O nome não pode estar vazia")
    private String name;

    @Email(message = "O e-mail deve ser válido")
    @NotBlank(message = "O e-mail não pode estar vazio")
    private String email;

    @NotBlank(message = "A senha não pode estar vazia")
    private String password;
}

package br.com.ifba.scedu.domain.entities.usuario.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequestDTO {
    private String login;
    private String email;
    private String password;
}

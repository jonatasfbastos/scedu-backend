package br.com.ifba.scedu.domain.entities.gestaoterceirizado.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {

    @NotNull(message = "Person ID is required.")
    private Long Id;

    @NotNull(message = "Person ID is required.")
    private Long IdPerson;

    @NotBlank(message = "Phone is required.")
    private String phone;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Position is required.")
    private String position;

    @NotBlank(message = "Enterprise is required.")
    private String enterprise;

    @NotBlank(message = "Department is required.")
    private String department;

    @NotNull(message = "Status is required.")
    private Boolean status;

    @NotBlank(message = "Observations is required.")
    private String observations;
}

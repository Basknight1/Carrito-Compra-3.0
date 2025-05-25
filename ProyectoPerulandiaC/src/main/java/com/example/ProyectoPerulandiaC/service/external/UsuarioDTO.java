package com.example.ProyectoPerulandiaC.service.external;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String run;
    private String correo;
}

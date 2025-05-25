package com.example.ProyectoPerulandiaC.service.external;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UsuarioClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public UsuarioDTO obtenerUsuarioPorId(Integer id) {
        String url = "http://localhost:8082/api/v1/usuarios/" + id;
        try {
            return restTemplate.getForObject(url, UsuarioDTO.class);
        } catch (Exception e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
            return null;
        }
    }
}

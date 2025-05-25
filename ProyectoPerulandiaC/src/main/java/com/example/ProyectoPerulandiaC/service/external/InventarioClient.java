package com.example.ProyectoPerulandiaC.service.external;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventarioClient {

    private final RestTemplate restTemplate;

    public InventarioClient() {
        this.restTemplate = new RestTemplate();
    }

    public PerfumeDTO obtenerPerfumePorId(Integer id) {
        String url = "http://localhost:8080/api/v1/perfumes/" + id;
         try {
        return restTemplate.getForObject(url, PerfumeDTO.class);
    } catch (Exception e) {
        System.err.println("Error al obtener perfume: " + e.getMessage());
        return null;
    }
        
    }
}

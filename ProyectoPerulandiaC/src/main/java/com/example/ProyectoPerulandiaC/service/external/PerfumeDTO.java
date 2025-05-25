package com.example.ProyectoPerulandiaC.service.external;

import lombok.Data;

@Data
public class PerfumeDTO {
    private Integer id;
    private String nombre;
    private Integer precio;
    private Integer descuento; 
    
    //opcionales
    private Integer stock;
    private Integer ml;
    private String marca;
    private String descripcion;
}

package com.example.ProyectoPerulandiaC.service.impl;

import com.example.ProyectoPerulandiaC.modelo.ItemCarrito;
import com.example.ProyectoPerulandiaC.repository.ItemCarritoRepository;
import com.example.ProyectoPerulandiaC.service.CarritoService;
import com.example.ProyectoPerulandiaC.service.descuento.Descuento;
import com.example.ProyectoPerulandiaC.service.descuento.DescuentoPorcentaje;
import com.example.ProyectoPerulandiaC.service.external.InventarioClient;
import com.example.ProyectoPerulandiaC.service.external.PerfumeDTO;
import com.example.ProyectoPerulandiaC.service.external.UsuarioClient;
import com.example.ProyectoPerulandiaC.service.external.UsuarioDTO;

import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CarritoServiceImpl implements CarritoService {

    private final ItemCarritoRepository itemCarritoRepository;
    private final InventarioClient inventarioClient;
    private final UsuarioClient usuarioClient;

    public CarritoServiceImpl(ItemCarritoRepository itemCarritoRepository, InventarioClient inventarioClient, UsuarioClient usuarioClient) {
        this.itemCarritoRepository = itemCarritoRepository;
        this.inventarioClient = inventarioClient;
        this.usuarioClient = usuarioClient;
    }

    @Override
public ItemCarrito agregarItem(Integer usuarioId, Integer perfumeId, Integer cantidad) {

    // Obtener los datos reales del perfume desde Inventario
    PerfumeDTO perfume = inventarioClient.obtenerPerfumePorId(perfumeId);
    if (perfume == null) {
        throw new RuntimeException("Perfume no encontrado");
    }

    UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorId(usuarioId);
    if (usuario == null) {
    throw new RuntimeException("Usuario no encontrado");
}
    System.out.println("Usuario obtenido: " + usuario.getNombre() + " " + usuario.getApellido());


    Integer porcentajeDescuento = perfume.getDescuento() != null ? perfume.getDescuento() : 0;

    // Aplicar descuento usando la lógica del microservicio
    Descuento descuento = new DescuentoPorcentaje(porcentajeDescuento);
    double precioConDescuento = descuento.aplicar(perfume.getPrecio());

    // Crear y guardar el ítem del carrito
    ItemCarrito item = new ItemCarrito();
    item.setCarritoId(usuarioId);
    item.setPerfumeId(perfumeId);
    item.setCantidad(cantidad);
    item.setPrecioUnitario(precioConDescuento);
    item.setDescuentoAplicado(porcentajeDescuento);

    return itemCarritoRepository.save(item);
}


    @Override
    public List<ItemCarrito> obtenerItemsDelCarrito(Integer usuarioId) {
        return itemCarritoRepository.findByCarritoId(usuarioId);
    }

    @Override
    public void eliminarItem(Integer itemId) {
        itemCarritoRepository.deleteById(itemId);
    }

    @Override
    public void vaciarCarrito(Integer usuarioId) {
        List<ItemCarrito> items = itemCarritoRepository.findByCarritoId(usuarioId);
        itemCarritoRepository.deleteAll(items);
    }
    @Override
public double calcularTotal(Integer usuarioId) {
    List<ItemCarrito> items = itemCarritoRepository.findByCarritoId(usuarioId);
    return items.stream()
                .mapToDouble(item -> item.getPrecioUnitario() * item.getCantidad())
                .sum();
}

}

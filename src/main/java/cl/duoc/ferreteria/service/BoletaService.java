package cl.duoc.ferreteria.service;

import cl.duoc.ferreteria.dto.CompraRequest;
import cl.duoc.ferreteria.dto.ItemCompraRequest;
import cl.duoc.ferreteria.exception.RecursoNoEncontradoException;
import cl.duoc.ferreteria.exception.StockInsuficienteException;
import cl.duoc.ferreteria.model.Boleta;
import cl.duoc.ferreteria.model.DetalleBoleta;
import cl.duoc.ferreteria.model.Producto;
import cl.duoc.ferreteria.repository.BoletaRepository;
import cl.duoc.ferreteria.repository.ProductoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoletaService {

    private final BoletaRepository boletaRepository;
    private final ProductoRepository productoRepository;

    public BoletaService(BoletaRepository boletaRepository, ProductoRepository productoRepository) {
        this.boletaRepository = boletaRepository;
        this.productoRepository = productoRepository;
    }

    public List<Boleta> listarTodas() {
        return boletaRepository.findAll();
    }

    public List<Boleta> listarPorUsuario(String correo) {
        return boletaRepository.findByUsuarioCorreoOrderByFechaDesc(correo);
    }

    public Boleta buscar(Long id) {
        return boletaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Boleta no encontrada: " + id));
    }

    @Transactional
    public Boleta crearCompra(String usuarioCorreo, CompraRequest request) {
        Boleta boleta = new Boleta();
        boleta.setUsuarioCorreo(usuarioCorreo);
        boleta.setFecha(LocalDateTime.now());

        double total = 0.0;

        for (ItemCompraRequest item : request.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(
                            () -> new RecursoNoEncontradoException("Producto no encontrado: " + item.getProductoId()));

            if (producto.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para " + producto.getNombre() + " (disponible: " + producto.getStock()
                                + ")");
            }

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            double subtotal = producto.getPrecio() * item.getCantidad();
            total += subtotal;

            DetalleBoleta detalle = new DetalleBoleta();
            detalle.setBoleta(boleta);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotal);
            boleta.getDetalles().add(detalle);
        }

        boleta.setTotal(total);
        return boletaRepository.save(boleta);
    }
}
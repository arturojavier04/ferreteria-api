
package cl.duoc.ferreteria.config;

import cl.duoc.ferreteria.model.Categoria;
import cl.duoc.ferreteria.model.Producto;
import cl.duoc.ferreteria.repository.CategoriaRepository;
import cl.duoc.ferreteria.repository.ProductoRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public DataInitializer(CategoriaRepository categoriaRepository,
            ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) {

        Categoria herramientas = obtenerOCrearCategoria(
                "Herramientas",
                "Herramientas manuales y eléctricas para construcción y hogar");

        Categoria construccion = obtenerOCrearCategoria(
                "Materiales de Construcción",
                "Cemento, áridos, adhesivos y obra gruesa");

        Categoria electricidad = obtenerOCrearCategoria(
                "Electricidad e Iluminación",
                "Cables, interruptores, tableros y luminarias LED");

        Categoria gasfiteria = obtenerOCrearCategoria(
                "Gasfitería",
                "Tuberías PVC, llaves, grifería y conexiones hidráulicas");

        if (productoRepository.count() == 0) {

            List<Producto> productos = List.of(
                    crearProducto("Taladro Percutor Inalámbrico 20V", herramientas,
                            "Incluye 2 baterías de litio, cargador rápido y maletín.", 89990.0, 15,
                            "/img/taladro.jpg"),
                    crearProducto("Martillo Carpintero Mango Fibra 16 oz", herramientas,
                            "Cabeza de acero forjado con grip antideslizante.", 12990.0, 30,
                            "/img/martillo.jpg"),
                    crearProducto("Set Destornilladores Aislados 6 Piezas", herramientas,
                            "Certificación 1000V punta imantada.", 16500.0, 25,
                            "/img/destornilladores.jpg"),
                    crearProducto("Cemento Polpaico Especial 25 kg", construccion,
                            "Saco de cemento de alta resistencia para albañilería y hormigón.", 5490.0, 80,
                            "/img/cemento.jpg"),
                    crearProducto("Adhesivo Cerámico Polvo 25 kg", construccion,
                            "Pegamento de alta adherencia para pisos y muros interiores.", 6290.0, 50,
                            "/img/adhesivo.jpg"),
                    crearProducto("Rollo Cable Eléctrico THHN 2.5 mm 100m", electricidad,
                            "Conductor de cobre aislado libre de halógenos.", 42990.0, 12,
                            "/img/cable.jpg"),
                    crearProducto("Foco Proyector LED Exterior 50W", electricidad,
                            "Luz fría 6500K, protección IP65 resistente a lluvia.", 14990.0, 20,
                            "/img/foco.jpg"),
                    crearProducto("Llave Monomando Lavaplatos Cromada", gasfiteria,
                            "Cartucho cerámico de 35 mm con flexibles incluidos.", 27990.0, 18,
                            "/img/monomando.jpg"));

            productoRepository.saveAll(productos);
        }
    }

    private Categoria obtenerOCrearCategoria(String nombre, String descripcion) {
        return categoriaRepository.findByNombreIgnoreCase(nombre)
                .orElseGet(() -> categoriaRepository.save(
                        new Categoria(nombre, descripcion)));
    }

    private Producto crearProducto(String nombre, Categoria categoria, String descripcion,
            Double precio, Integer stock, String imagen) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setCategoria(categoria);
        p.setDescripcion(descripcion);
        p.setPrecio(precio);
        p.setStock(stock);
        p.setImagen(imagen);
        return p;
    }
}

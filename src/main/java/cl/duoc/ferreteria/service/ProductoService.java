package cl.duoc.ferreteria.service;

import cl.duoc.ferreteria.exception.RecursoNoEncontradoException;
import cl.duoc.ferreteria.model.Producto;
import cl.duoc.ferreteria.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listar() {
        return repository.findAll();
    }

    public Producto buscar(Long id) {
        return repository.findById(id).orElseThrow(() ->
            new RecursoNoEncontradoException("Producto no encontrado: " + id));
    }

    public Producto guardar(Producto producto) {
        return repository.save(producto);
    }

    public Producto actualizar(Long id, Producto datos) {
        Producto actual = buscar(id);
        actual.setNombre(datos.getNombre());
        actual.setCategoria(datos.getCategoria());
        actual.setDescripcion(datos.getDescripcion());
        actual.setPrecio(datos.getPrecio());
        actual.setStock(datos.getStock());

        return repository.save(actual);
    }

    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }
}

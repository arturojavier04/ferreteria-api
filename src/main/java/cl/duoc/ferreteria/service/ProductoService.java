package cl.duoc.ferreteria.service;

import cl.duoc.ferreteria.dto.ProductoRequest;
import cl.duoc.ferreteria.exception.RecursoNoEncontradoException;
import cl.duoc.ferreteria.model.Categoria;
import cl.duoc.ferreteria.model.Producto;
import cl.duoc.ferreteria.repository.ProductoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    private final ProductoRepository repository;
    private final CategoriaService categoriaService;

    public ProductoService(ProductoRepository repository, CategoriaService categoriaService) {
        this.repository = repository;
        this.categoriaService = categoriaService;
    }

    public List<Producto> listar() {
        return repository.findAll();
    }

    public List<Producto> listarPorCategoria(Long categoriaId) {
        return repository.findByCategoriaId(categoriaId);
    }

    public Producto buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado: " + id));
    }

    public Producto guardar(ProductoRequest req) {
        Categoria cat = categoriaService.buscar(req.getCategoriaId());
        Producto p = new Producto();
        p.setNombre(req.getNombre());
        p.setCategoria(cat);
        p.setDescripcion(req.getDescripcion());
        p.setPrecio(req.getPrecio());
        p.setStock(req.getStock());
        p.setImagen(req.getImagen());
        return repository.save(p);
    }

    public Producto actualizar(Long id, ProductoRequest req) {
        Producto actual = buscar(id);
        Categoria cat = categoriaService.buscar(req.getCategoriaId());
        actual.setNombre(req.getNombre());
        actual.setCategoria(cat);
        actual.setDescripcion(req.getDescripcion());
        actual.setPrecio(req.getPrecio());
        actual.setStock(req.getStock());
        actual.setImagen(req.getImagen());
        return repository.save(actual);
    }

    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }
}
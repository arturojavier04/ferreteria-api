package cl.duoc.ferreteria.service;

import cl.duoc.ferreteria.exception.RecursoNoEncontradoException;
import cl.duoc.ferreteria.model.Categoria;
import cl.duoc.ferreteria.repository.CategoriaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> listar() {
        return repository.findAll();
    }

    public Categoria buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada: " + id));
    }

    public Categoria guardar(Categoria categoria) {
        return repository.save(categoria);
    }

    public Categoria actualizar(Long id, Categoria datos) {
        Categoria actual = buscar(id);
        actual.setNombre(datos.getNombre());
        actual.setDescripcion(datos.getDescripcion());
        return repository.save(actual);
    }

    public void eliminar(Long id) {
        repository.delete(buscar(id));
    }
}
package cl.duoc.ferreteria.controller;

import cl.duoc.ferreteria.dto.ProductoRequest;
import cl.duoc.ferreteria.model.Producto;
import cl.duoc.ferreteria.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Catálogo y administración de productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar productos")
    @GetMapping
    public List<Producto> listar(@RequestParam(required = false) Long categoriaId) {
        return (categoriaId != null) ? service.listarPorCategoria(categoriaId) : service.listar();
    }

    @Operation(summary = "Buscar producto por id")
    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @Operation(summary = "Crear producto (Solo ADMIN)")
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(request));
    }

    @Operation(summary = "Actualizar producto (Solo ADMIN)")
    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        return service.actualizar(id, request);
    }

    @Operation(summary = "Eliminar producto (Solo ADMIN)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
package cl.duoc.ferreteria.controller;

import cl.duoc.ferreteria.dto.CompraRequest;
import cl.duoc.ferreteria.model.Boleta;
import cl.duoc.ferreteria.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boletas")
@Tag(name = "Boletas y Órdenes", description = "Registro de compras y consulta de órdenes")
public class BoletaController {

    private final BoletaService service;

    public BoletaController(BoletaService service) {
        this.service = service;
    }

    @Operation(summary = "Generar boleta de compra (CLIENTE / ADMIN)")
    @PostMapping
    public ResponseEntity<Boleta> comprar(@Valid @RequestBody CompraRequest request, Authentication auth) {
        Boleta creada = service.crearCompra(auth.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @Operation(summary = "Listar boletas (VENDEDOR/ADMIN ven todas; CLIENTE ve las suyas)")
    @GetMapping
    public List<Boleta> listar(Authentication auth) {
        boolean esStaff = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_VENDEDOR"));
        return esStaff ? service.listarTodas() : service.listarPorUsuario(auth.getName());
    }

    @Operation(summary = "Ver detalle de una boleta por id")
    @GetMapping("/{id}")
    public Boleta buscar(@PathVariable Long id) {
        return service.buscar(id);
    }
}
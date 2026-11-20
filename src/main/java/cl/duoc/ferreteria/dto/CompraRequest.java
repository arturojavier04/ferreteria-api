package cl.duoc.ferreteria.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;

@Data
public class CompraRequest {
    @NotEmpty(message = "La boleta debe incluir al menos un producto")
    @Valid
    private List<ItemCompraRequest> items;
}
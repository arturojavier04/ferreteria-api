package cl.duoc.ferreteria.repository;

import cl.duoc.ferreteria.model.Boleta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    List<Boleta> findByUsuarioCorreoOrderByFechaDesc(String usuarioCorreo);
}
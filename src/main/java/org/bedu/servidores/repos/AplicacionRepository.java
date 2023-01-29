package org.bedu.servidores.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.bedu.servidores.model.Aplicacion;

import java.util.List;

public interface AplicacionRepository extends JpaRepository<Aplicacion,Long> {
    List<Aplicacion> findByServidor_Id(Long id);

}

package org.bedu.servidores.repos;
import org.bedu.servidores.model.AppSrv;
import org.hibernate.annotations.Any;
import org.springframework.data.jpa.repository.JpaRepository;
import org.bedu.servidores.model.Aplicacion;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AplicacionRepository extends JpaRepository<Aplicacion,Long> {
    List<Aplicacion> findByServidor_Id(Long servidorId);
    @Query(value="SELECT new org.bedu.servidores.model.AppSrv(a.id,a.nombre,a.version,s.id servidorId,s.nombre servidorNombre) " +
            "FROM Servidor s JOIN s.aplicaciones a",
    nativeQuery = false)
    List<AppSrv> findAllWithServidor();

}

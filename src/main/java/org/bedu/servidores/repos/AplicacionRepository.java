package org.bedu.servidores.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.bedu.servidores.model.Aplicacion;
public interface AplicacionRepository extends JpaRepository<Aplicacion,Long> {

}

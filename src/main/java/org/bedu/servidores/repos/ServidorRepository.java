package org.bedu.servidores.repos;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.bedu.servidores.model.Servidor;
public interface ServidorRepository extends JpaRepository<Servidor,Long> {

}

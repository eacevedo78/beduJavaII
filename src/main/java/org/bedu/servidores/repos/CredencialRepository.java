package org.bedu.servidores.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.bedu.servidores.model.Credencial;
public interface CredencialRepository extends JpaRepository<Credencial,Long> {

}

package org.bedu.servidores.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.bedu.servidores.model.Credencial;

import java.util.List;

public interface CredencialRepository extends JpaRepository<Credencial,Long> {
    List<Credencial> findByUsuario_Id(Long usuarioId);

}

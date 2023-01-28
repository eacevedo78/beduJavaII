package org.bedu.servidores.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.bedu.servidores.model.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

}

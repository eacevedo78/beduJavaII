package org.bedu.servidores.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.bedu.servidores.model.Usuario;
import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario>  findOneByCorreo(String correo);

}

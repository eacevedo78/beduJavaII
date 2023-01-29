package org.bedu.servidores.security;

import org.bedu.servidores.model.Usuario;
import org.bedu.servidores.repos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findOneByCorreo(username).
                orElseThrow(()-> new UsernameNotFoundException("El correo no se encontro"));
        return new UserDetailsImpl(usuario);
    }
}

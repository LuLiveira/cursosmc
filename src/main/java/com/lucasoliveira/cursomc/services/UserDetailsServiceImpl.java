package com.lucasoliveira.cursomc.services;

import com.lucasoliveira.cursomc.domain.Cliente;
import com.lucasoliveira.cursomc.repositories.ClienteRepository;
import com.lucasoliveira.cursomc.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Cliente cliente = clienteRepository.findByEmail(email);

        if(cliente == null){
            throw new UsernameNotFoundException(email);
        }
        UserSpringSecurity security = new UserSpringSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());

        return security;
    }
}

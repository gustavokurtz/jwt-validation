package estudo.api.jwt.projeto.security;

import estudo.api.jwt.projeto.model.UserModel;
import estudo.api.jwt.projeto.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca o usuário pelo nome
        UserModel user = userRepository.findByNome(username);

        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        // Retorna um UserDetails com usuário e senha
        // O ArrayList vazio representa as authorities (perfis de acesso)
        return new User(user.getNome(), user.getPassword(), new ArrayList<>());
    }
}
package estudo.api.jwt.projeto.service;

import estudo.api.jwt.projeto.model.UserModel;
import estudo.api.jwt.projeto.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    // Injeção via construtor (recomendado)
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserModel> list(){
        return repository.findAll();
    }

    @Transactional
    public UserModel register(UserModel userModel){

        if (userModel.getNome() == null || userModel.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if(userModel.getPassword() == null || userModel.getPassword().isBlank()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }

        UserModel existingUser = repository.findByNome(userModel.getNome());

        if (existingUser != null) {
            throw new IllegalArgumentException("Já existe um usuário com este nome!");
        }

        // Criptografa a senha antes de salvar
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

        return repository.save(userModel);
    }

    public UserModel listUserById(Long id){
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public UserModel updateUser(UserModel newUserData, Long id) {
        UserModel existingUser = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não existe!"));

        // Atualiza apenas os campos permitidos
        existingUser.setNome(newUserData.getNome());

        // Se a senha estiver presente, criptografa antes de salvar
        if (newUserData.getPassword() != null && !newUserData.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(newUserData.getPassword()));
        }

        return repository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id){
        repository.deleteById(id);
    }
}
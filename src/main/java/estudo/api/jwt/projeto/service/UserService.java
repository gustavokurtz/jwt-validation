package estudo.api.jwt.projeto.service;

import estudo.api.jwt.projeto.model.UserModel;
import estudo.api.jwt.projeto.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    // Injeção via construtor (recomendado)
    public UserService(UserRepository repository) {
        this.repository = repository;
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
        existingUser.setPassword(newUserData.getPassword());

        // Se houver senha, adicione hash aqui!
        return repository.save(existingUser);
    }


    @Transactional
    public void deleteUser(Long id){
        repository.deleteById(id);
    }

}

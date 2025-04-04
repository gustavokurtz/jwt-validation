package estudo.api.jwt.projeto.repository;

import estudo.api.jwt.projeto.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long > {
    public UserModel findByNome(String nome);
}

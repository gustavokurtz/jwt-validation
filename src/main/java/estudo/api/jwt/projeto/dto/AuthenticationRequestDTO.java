package estudo.api.jwt.projeto.dto;

public class AuthenticationRequestDTO {

    private String nome; // Alterado de username para nome
    private String password;

    // Construtor padrão necessário para deserialização JSON
    public AuthenticationRequestDTO() {
    }

    public AuthenticationRequestDTO(String nome, String password) {
        this.nome = nome;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
package estudo.api.jwt.projeto.controller;

import estudo.api.jwt.projeto.dto.UserDTO;
import estudo.api.jwt.projeto.model.UserModel;
import estudo.api.jwt.projeto.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class RegisterController {

    private final UserService service;

    public RegisterController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> list() {
        List<UserDTO> users = service.list().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserModel user) {
        UserModel savedUser = service.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> listUserById(@PathVariable Long id) {
        UserModel user = service.listUserById(id);
        return ResponseEntity.ok(convertToDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserModel updatedUser
    ) {
        UserModel user = service.updateUser(updatedUser, id);
        return ResponseEntity.ok(convertToDTO(user));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        service.deleteUser(id);

    }

    // Método para converter UserModel → UserDTO
    private UserDTO convertToDTO(UserModel user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        return dto;
    }
}
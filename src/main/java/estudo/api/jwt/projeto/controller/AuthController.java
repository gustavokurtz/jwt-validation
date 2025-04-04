package estudo.api.jwt.projeto.controller;

import estudo.api.jwt.projeto.dto.AuthenticationRequestDTO;
import estudo.api.jwt.projeto.dto.AuthenticationResponseDTO;
import estudo.api.jwt.projeto.security.JwtUtil;
import estudo.api.jwt.projeto.security.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsServiceImpl userDetailsService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequest) throws Exception {
        try {
            // Autentica o usuário com nome e password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getNome(), // Alterado para getNome()
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciais inválidas", e);
        }

        // Se autenticação for bem-sucedida, gera o token JWT
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getNome()); // Alterado para getNome()

        final String jwt = jwtUtil.generateToken(userDetails);

        // Retorna o token como resposta
        return ResponseEntity.ok(new AuthenticationResponseDTO(jwt));
    }

    // Endpoint de teste para verificar a configuração
    @PostMapping("/test")
    public ResponseEntity<String> testAuth() {
        return ResponseEntity.ok("Endpoint de autenticação está funcionando!");
    }
}
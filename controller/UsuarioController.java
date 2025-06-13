package com.ecosmart.eco.controller;

import com.ecosmart.eco.model.Usuario;
import com.ecosmart.eco.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET /api/usuarios - Buscar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> buscarTodos() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // GET /api/usuarios/{id} - Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/usuarios - Criar novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        // Verificar se email já existe
        if (usuarioService.existePorEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        Usuario novoUsuario = usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    // PUT /api/usuarios/{id} - Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id,
                                             @RequestBody Usuario usuario) {
        if (!usuarioService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        usuario.setId_usuario(id);
        Usuario usuarioAtualizado = usuarioService.atualizar(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // DELETE /api/usuarios/{id} - Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!usuarioService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/usuarios/email/{email} - Buscar por email
//    @GetMapping("/email/{email}")
//    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
//        Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);
//        return usuario.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
    @PostMapping("/email")
    public ResponseEntity<Usuario> buscarPorEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);
        return usuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/usuarios/count - Contar usuários
    @GetMapping("/count")
    public ResponseEntity<Long> contar() {
        long count = usuarioService.contar();
        return ResponseEntity.ok(count);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String senha = loginRequest.get("senha");

        // Buscar usuário por email
        Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);

        if (usuario.isPresent()) {
            // Verificar se a senha confere (assumindo que você tem um campo senha)
            if (usuario.get().getSenha().equals(senha)) {
                // Login bem-sucedido
                return ResponseEntity.ok(Map.of(
                        "message", "Login realizado com sucesso",
                        "usuario", usuario.get()
                ));
            } else {
                // Senha incorreta
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Senha incorreta"));
            }
        }

        // Usuário não encontrado
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Email não encontrado"));
    }
}
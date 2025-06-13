package com.ecosmart.eco.controller;

import com.ecosmart.eco.model.Relatorio;
import com.ecosmart.eco.model.Ambiente;
import com.ecosmart.eco.model.Usuario;
import com.ecosmart.eco.service.RelatorioService;
import com.ecosmart.eco.service.AmbienteService;
import com.ecosmart.eco.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private AmbienteService ambienteService;

    @Autowired
    private UsuarioService usuarioService;

    // ===== OPERAÇÕES BÁSICAS CRUD =====

    /**
     * GET /api/relatorios
     * Busca todos os relatórios
     */
    @GetMapping
    public ResponseEntity<List<Relatorio>> buscarTodos() {
        try {
            List<Relatorio> relatorios = relatorioService.buscarTodos();
            return ResponseEntity.ok(relatorios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/relatorios/{id}
     * Busca relatório por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Relatorio> buscarPorId(@PathVariable Integer id) {
        try {
            Optional<Relatorio> relatorio = relatorioService.buscarPorId(id);
            return relatorio.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/relatorios/{id}/detalhes
     * Busca relatório por ID com detalhes (ambiente e usuário)
     */
    @GetMapping("/{id}/detalhes")
    public ResponseEntity<Relatorio> buscarPorIdComDetalhes(@PathVariable Integer id) {
        try {
            Optional<Relatorio> relatorio = relatorioService.buscarPorIdComDetalhes(id);
            return relatorio.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /api/relatorios
     * Cria novo relatório
     */
    @PostMapping
    public ResponseEntity<Relatorio> criar(@RequestBody Relatorio relatorio) {
        try {
            // Validações básicas
            if (relatorio.getAmbiente() == null || relatorio.getUsuario() == null) {
                return ResponseEntity.badRequest().build();
            }

            Relatorio novoRelatorio = relatorioService.salvar(relatorio);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoRelatorio);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /api/relatorios/criar
     * Cria relatório com IDs de ambiente e usuário
     */
    @PostMapping("/criar")
    public ResponseEntity<Relatorio> criarComIds(
            @RequestParam Integer ambienteId,
            @RequestParam Integer usuarioId,
            @RequestParam(required = false) String dadosRelatorio) {
        try {
            Relatorio relatorio = relatorioService.criarRelatorio(ambienteId, usuarioId, dadosRelatorio);
            return ResponseEntity.status(HttpStatus.CREATED).body(relatorio);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * PUT /api/relatorios/{id}
     * Atualiza relatório existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Relatorio> atualizar(@PathVariable Integer id, @RequestBody Relatorio relatorio) {
        try {
            Optional<Relatorio> relatorioExistente = relatorioService.buscarPorId(id);
            if (relatorioExistente.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            relatorio.setIdRelatorio(id);
            Relatorio relatorioAtualizado = relatorioService.salvar(relatorio);
            return ResponseEntity.ok(relatorioAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE /api/relatorios/{id}
     * Deleta relatório por ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        try {
            Optional<Relatorio> relatorio = relatorioService.buscarPorId(id);
            if (relatorio.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            relatorioService.deletarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== CONSULTAS POR AMBIENTE =====

    /**
     * GET /api/relatorios/ambiente/{ambienteId}
     * Busca relatórios por ambiente
     */
    @GetMapping("/ambiente/{ambienteId}")
    public ResponseEntity<List<Relatorio>> buscarPorAmbiente(@PathVariable Integer ambienteId) {
        try {
            List<Relatorio> relatorios = relatorioService.buscarPorIdAmbiente(ambienteId);
            return ResponseEntity.ok(relatorios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/relatorios/ambiente/{ambienteId}/count
     * Conta relatórios por ambiente
     */
    @GetMapping("/ambiente/{ambienteId}/count")
    public ResponseEntity<Long> contarPorAmbiente(@PathVariable Integer ambienteId) {
        try {
            Long count = relatorioService.contarPorAmbiente(ambienteId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== CONSULTAS POR USUÁRIO =====

    /**
     * GET /api/relatorios/usuario/{usuarioId}
     * Busca relatórios por usuário
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Relatorio>> buscarPorUsuario(@PathVariable Integer usuarioId) {
        try {
            List<Relatorio> relatorios = relatorioService.buscarPorIdUsuario(usuarioId);
            return ResponseEntity.ok(relatorios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/relatorios/usuario/{usuarioId}/count
     * Conta relatórios por usuário
     */
    @GetMapping("/usuario/{usuarioId}/count")
    public ResponseEntity<Long> contarPorUsuario(@PathVariable Integer usuarioId) {
        try {
            Long count = relatorioService.contarPorUsuario(usuarioId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/relatorios/usuario/{usuarioId}/recentes
     * Busca relatórios recentes do usuário
     */
    @GetMapping("/usuario/{usuarioId}/recentes")
    public ResponseEntity<List<Relatorio>> buscarRelatoriosRecentes(@PathVariable Integer usuarioId) {
        try {
            List<Relatorio> relatorios = relatorioService.buscarRelatoriosRecentesDoUsuario(usuarioId);
            return ResponseEntity.ok(relatorios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== CONSULTAS COMBINADAS =====

    /**
     * GET /api/relatorios/ambiente/{ambienteId}/usuario/{usuarioId}
     * Busca relatórios por ambiente e usuário
     */
    @GetMapping("/ambiente/{ambienteId}/usuario/{usuarioId}")
    public ResponseEntity<List<Relatorio>> buscarPorAmbienteEUsuario(
            @PathVariable Integer ambienteId,
            @PathVariable Integer usuarioId) {
        try {
            List<Relatorio> relatorios = relatorioService.buscarPorIdAmbienteEIdUsuario(ambienteId, usuarioId);
            return ResponseEntity.ok(relatorios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== VERIFICAÇÕES DE EXISTÊNCIA =====

    /**
     * GET /api/relatorios/existe/ambiente/{ambienteId}/usuario/{usuarioId}
     * Verifica se existe relatório para ambiente e usuário
     */
    @GetMapping("/existe/ambiente/{ambienteId}/usuario/{usuarioId}")
    public ResponseEntity<Boolean> existePorAmbienteEUsuario(
            @PathVariable Integer ambienteId,
            @PathVariable Integer usuarioId) {
        try {
            boolean existe = relatorioService.existePorIdAmbienteEIdUsuario(ambienteId, usuarioId);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== VERIFICAÇÕES DE ACESSO =====

    /**
     * GET /api/relatorios/{relatorioId}/acesso/usuario/{usuarioId}
     * Verifica se usuário tem acesso ao relatório
     */
    @GetMapping("/{relatorioId}/acesso/usuario/{usuarioId}")
    public ResponseEntity<Boolean> verificarAcesso(
            @PathVariable Integer relatorioId,
            @PathVariable Integer usuarioId) {
        try {
            boolean temAcesso = relatorioService.usuarioTemAcessoAoRelatorio(relatorioId, usuarioId);
            return ResponseEntity.ok(temAcesso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== OPERAÇÕES EM LOTE =====

    /**
     * DELETE /api/relatorios/ambiente/{ambienteId}
     * Remove todos os relatórios de um ambiente
     */
    @DeleteMapping("/ambiente/{ambienteId}")
    public ResponseEntity<Void> removerRelatoriosDoAmbiente(@PathVariable Integer ambienteId) {
        try {
            relatorioService.removerRelatoriosDoAmbiente(ambienteId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE /api/relatorios/usuario/{usuarioId}
     * Remove todos os relatórios de um usuário
     */
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> removerRelatoriosDoUsuario(@PathVariable Integer usuarioId) {
        try {
            relatorioService.removerRelatoriosDoUsuario(usuarioId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== ESTATÍSTICAS =====

    /**
     * GET /api/relatorios/count
     * Conta total de relatórios
     */
    @GetMapping("/count")
    public ResponseEntity<Long> contarTodos() {
        try {
            long count = relatorioService.contarTodos();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ===== HEALTH CHECK =====

    /**
     * GET /api/relatorios/health
     * Verifica se o serviço está funcionando
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Relatorio Service está funcionando!");
    }
}
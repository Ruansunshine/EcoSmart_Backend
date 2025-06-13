package com.ecosmart.eco.controller;

import com.ecosmart.eco.model.Ambiente;
import com.ecosmart.eco.model.Objeto;
import com.ecosmart.eco.service.AmbienteService;
import com.ecosmart.eco.service.ObjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ambientes")
public class AmbienteController {

    @Autowired
    private AmbienteService ambienteService;

    @Autowired
    private ObjetoService objetoService;

    // GET /api/ambientes - Buscar todos os ambientes
    @GetMapping
    public ResponseEntity<List<Ambiente>> buscarTodos() {
        List<Ambiente> ambientes = ambienteService.buscarTodos();
        return ResponseEntity.ok(ambientes);
    }

    // GET /api/ambientes/{id} - Buscar ambiente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ambiente> buscarPorId(@PathVariable Integer id) {
        Optional<Ambiente> ambiente = ambienteService.buscarPorId(id);
        return ambiente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/ambientes - Criar novo ambiente (CORRIGIDO para novo relacionamento)
    @PostMapping
    public ResponseEntity<Ambiente> criar(@RequestBody Ambiente ambiente) {
        try {
            // Verificar se nome já existe
            if (ambienteService.existePorNome(ambiente.getNome())) {
                return ResponseEntity.badRequest().build();
            }

            // Validar objetos se fornecidos (agora é uma lista)
            if (ambiente.getObjetos() != null && !ambiente.getObjetos().isEmpty()) {
                for (Objeto objeto : ambiente.getObjetos()) {
                    if (objeto.getIdObjeto() != null) {
                        Optional<Objeto> objetoOptional = objetoService.buscarPorId(objeto.getIdObjeto());
                        if (objetoOptional.isEmpty()) {
                            return ResponseEntity.badRequest().build(); // Objeto não encontrado
                        }
                        // Atualizar o objeto com dados completos
                        Objeto objetoCompleto = objetoOptional.get();
                        objetoCompleto.setAmbiente(ambiente); // Setar o ambiente no objeto
                        objeto = objetoCompleto;
                    }
                }
            }

            // Salvar o ambiente
            Ambiente novoAmbiente = ambienteService.salvar(ambiente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAmbiente);

        } catch (Exception e) {
            // Log do erro para debug
            System.err.println("Erro ao criar ambiente: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PUT /api/ambientes/{id} - Atualizar ambiente (CORRIGIDO)
    @PutMapping("/{id}")
    public ResponseEntity<Ambiente> atualizar(@PathVariable Integer id,
                                              @RequestBody Ambiente ambiente) {
        try {
            if (!ambienteService.existePorId(id)) {
                return ResponseEntity.notFound().build();
            }

            // Validar objetos se fornecidos
            if (ambiente.getObjetos() != null && !ambiente.getObjetos().isEmpty()) {
                for (Objeto objeto : ambiente.getObjetos()) {
                    if (objeto.getIdObjeto() != null) {
                        Optional<Objeto> objetoOptional = objetoService.buscarPorId(objeto.getIdObjeto());
                        if (objetoOptional.isEmpty()) {
                            return ResponseEntity.badRequest().build();
                        }
                        // Atualizar referência do ambiente no objeto
                        Objeto objetoCompleto = objetoOptional.get();
                        objetoCompleto.setAmbiente(ambiente);
                        objeto = objetoCompleto;
                    }
                }
            }

            ambiente.setIdAmbiente(id);
            Ambiente ambienteAtualizado = ambienteService.atualizar(ambiente);
            return ResponseEntity.ok(ambienteAtualizado);

        } catch (Exception e) {
            System.err.println("Erro ao atualizar ambiente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE /api/ambientes/{id} - Deletar ambiente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!ambienteService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        ambienteService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/ambientes/nome/{nome} - Buscar por nome exato
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Ambiente>> buscarPorNome(@PathVariable String nome) {
        List<Ambiente> ambientes = ambienteService.buscarPorNome(nome);
        return ResponseEntity.ok(ambientes);
    }

    // GET /api/ambientes/search/{nome} - Buscar por nome contendo
    @GetMapping("/search/{nome}")
    public ResponseEntity<List<Ambiente>> buscarPorNomeContendo(@PathVariable String nome) {
        List<Ambiente> ambientes = ambienteService.buscarPorNomeContendo(nome);
        return ResponseEntity.ok(ambientes);
    }

    // GET /api/ambientes/{id}/usuarios - Buscar ambiente com usuários
    @GetMapping("/{id}/usuarios")
    public ResponseEntity<Ambiente> buscarPorIdComUsuarios(@PathVariable Integer id) {
        Optional<Ambiente> ambiente = ambienteService.buscarPorIdComUsuarios(id);
        return ambiente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/ambientes/{id}/relatorios - Buscar ambiente com relatórios
    @GetMapping("/{id}/relatorios")
    public ResponseEntity<Ambiente> buscarPorIdComRelatorios(@PathVariable Integer id) {
        Optional<Ambiente> ambiente = ambienteService.buscarPorIdComRelatorios(id);
        return ambiente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/ambientes/{id}/objetos - Buscar ambiente com objetos (NOVO - RESTful)
    @GetMapping("/{id}/objetos")
    public ResponseEntity<List<Objeto>> buscarObjetosPorAmbiente(@PathVariable Integer id) {
        Optional<Ambiente> ambiente = ambienteService.buscarPorId(id);
        if (ambiente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ambiente.get().getObjetos());
    }

    // POST /api/ambientes/{id}/objetos/{objetoId} - Adicionar objeto ao ambiente (RESTful)
    @PostMapping("/{id}/objetos/{objetoId}")
    public ResponseEntity<Ambiente> adicionarObjeto(@PathVariable Integer id,
                                                    @PathVariable Integer objetoId) {
        try {
            Optional<Ambiente> ambienteOpt = ambienteService.buscarPorId(id);
            Optional<Objeto> objetoOpt = objetoService.buscarPorId(objetoId);

            if (ambienteOpt.isEmpty() || objetoOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Ambiente ambiente = ambienteOpt.get();
            Objeto objeto = objetoOpt.get();

            // Setar o ambiente no objeto
            objeto.setAmbiente(ambiente);
            objetoService.salvar(objeto);

            // Recarregar o ambiente com os objetos atualizados
            Ambiente ambienteAtualizado = ambienteService.buscarPorId(id).get();
            return ResponseEntity.ok(ambienteAtualizado);

        } catch (Exception e) {
            System.err.println("Erro ao adicionar objeto ao ambiente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE /api/ambientes/{id}/objetos/{objetoId} - Remover objeto do ambiente (RESTful)
    @DeleteMapping("/{id}/objetos/{objetoId}")
    public ResponseEntity<Void> removerObjeto(@PathVariable Integer id,
                                              @PathVariable Integer objetoId) {
        try {
            Optional<Ambiente> ambienteOpt = ambienteService.buscarPorId(id);
            Optional<Objeto> objetoOpt = objetoService.buscarPorId(objetoId);

            if (ambienteOpt.isEmpty() || objetoOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Objeto objeto = objetoOpt.get();

            // Verificar se o objeto realmente pertence ao ambiente
            if (objeto.getAmbiente() == null || !objeto.getAmbiente().getIdAmbiente().equals(id)) {
                return ResponseEntity.badRequest().build();
            }

            // Remover a associação
            objeto.setAmbiente(null);
            objetoService.salvar(objeto);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            System.err.println("Erro ao remover objeto do ambiente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /api/ambientes/{id}/completo - Buscar ambiente completo
    @GetMapping("/{id}/completo")
    public ResponseEntity<Ambiente> buscarPorIdCompleto(@PathVariable Integer id) {
        Optional<Ambiente> ambiente = ambienteService.buscarPorIdCompleto(id);
        return ambiente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/ambientes/count - Contar ambientes
    @GetMapping("/count")
    public ResponseEntity<Long> contar() {
        long count = ambienteService.contar();
        return ResponseEntity.ok(count);
    }

    // GET /api/ambientes/exists/nome/{nome} - Verificar se existe por nome
    @GetMapping("/exists/nome/{nome}")
    public ResponseEntity<Boolean> existePorNome(@PathVariable String nome) {
        boolean existe = ambienteService.existePorNome(nome);
        return ResponseEntity.ok(existe);
    }

    // GET /api/ambientes/tipo-objeto/{tipoObjeto} - Buscar ambientes por tipo de objeto (RESTful)
    @GetMapping("/tipo-objeto/{tipoObjeto}")
    public ResponseEntity<List<Ambiente>> buscarPorTipoObjeto(@PathVariable String tipoObjeto) {
        List<Ambiente> ambientes = ambienteService.buscarPorTipoObjeto(tipoObjeto);
        return ResponseEntity.ok(ambientes);
    }

    // GET /api/ambientes/nome-objeto/{nomeObjeto} - Buscar ambientes por nome do objeto (RESTful)
    @GetMapping("/nome-objeto/{nomeObjeto}")
    public ResponseEntity<List<Ambiente>> buscarPorNomeObjeto(@PathVariable String nomeObjeto) {
        List<Ambiente> ambientes = ambienteService.buscarPorNomeObjeto(nomeObjeto);
        return ResponseEntity.ok(ambientes);
    }

    // GET /api/ambientes/status-objeto/{status} - Buscar ambientes por status do objeto (RESTful)
    @GetMapping("/status-objeto/{status}")
    public ResponseEntity<List<Ambiente>> buscarPorStatusObjeto(@PathVariable String status) {
        List<Ambiente> ambientes = ambienteService.buscarPorStatusObjeto(status);
        return ResponseEntity.ok(ambientes);
    }

    // GET /api/ambientes/objetos-ativos/{ativo} - Buscar ambientes com objetos ativos (RESTful)
    @GetMapping("/objetos-ativos/{ativo}")
    public ResponseEntity<List<Ambiente>> buscarPorObjetoAtivo(@PathVariable Integer ativo) {
        List<Ambiente> ambientes = ambienteService.buscarPorObjetoAtivo(ativo);
        return ResponseEntity.ok(ambientes);
    }

    // GET /api/ambientes/potencia-maior/{potencia} - Buscar ambientes com objetos de potência maior (RESTful)
    @GetMapping("/potencia-maior/{potencia}")
    public ResponseEntity<List<Ambiente>> buscarPorPotenciaObjetoMaiorQue(@PathVariable Integer potencia) {
        List<Ambiente> ambientes = ambienteService.buscarPorPotenciaObjetoMaiorQue(potencia);
        return ResponseEntity.ok(ambientes);
    }

    // GET /api/ambientes/potencia-entre/{min}/{max} - Buscar ambientes por faixa de potência (RESTful)
    @GetMapping("/potencia-entre/{min}/{max}")
    public ResponseEntity<List<Ambiente>> buscarPorFaixaPotenciaObjeto(@PathVariable Integer min,
                                                                       @PathVariable Integer max) {
        List<Ambiente> ambientes = ambienteService.buscarPorFaixaPotenciaObjeto(min, max);
        return ResponseEntity.ok(ambientes);
    }
}
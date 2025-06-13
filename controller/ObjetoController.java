package com.ecosmart.eco.controller;

import com.ecosmart.eco.model.Objeto;
import com.ecosmart.eco.model.Ambiente;
import com.ecosmart.eco.service.ObjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/objetos")
public class ObjetoController {

    @Autowired
    private ObjetoService objetoService;

    // GET /api/objetos - Buscar todos os objetos
    @GetMapping
    public ResponseEntity<List<Objeto>> buscarTodos() {
        List<Objeto> objetos = objetoService.buscarTodos();
        return ResponseEntity.ok(objetos);
    }

    // GET /api/objetos/{id} - Buscar objeto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Objeto> buscarPorId(@PathVariable Integer id) {
        Optional<Objeto> objeto = objetoService.buscarPorId(id);
        return objeto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/objetos - Criar novo objeto
    @PostMapping
    public ResponseEntity<Objeto> criar(@RequestBody Objeto objeto) {
        // Verificar se nome já existe
        if (objetoService.existePorNome(objeto.getNomeObjeto())) {
            return ResponseEntity.badRequest().build();
        }

        Objeto novoObjeto = objetoService.salvar(objeto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoObjeto);
    }

    // PUT /api/objetos/{id} - Atualizar objeto
    @PutMapping("/{id}")
    public ResponseEntity<Objeto> atualizar(@PathVariable Integer id,
                                            @RequestBody Objeto objeto) {
        if (!objetoService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        objeto.setIdObjeto(id);
        Objeto objetoAtualizado = objetoService.atualizar(objeto);
        return ResponseEntity.ok(objetoAtualizado);
    }

    // DELETE /api/objetos/{id} - Deletar objeto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (!objetoService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        objetoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/objetos/nome/{nome} - Buscar por nome exato
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Objeto>> buscarPorNome(@PathVariable String nome) {
        List<Objeto> objetos = objetoService.buscarPorNome(nome);
        return ResponseEntity.ok(objetos);
    }

    // GET /api/objetos/search/{nome} - Buscar por nome contendo
    @GetMapping("/search/{nome}")
    public ResponseEntity<List<Objeto>> buscarPorNomeContendo(@PathVariable String nome) {
        List<Objeto> objetos = objetoService.buscarPorNomeContendo(nome);
        return ResponseEntity.ok(objetos);
    }

    // GET /api/objetos/tipo/{tipo} - Buscar por tipo exato
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Objeto>> buscarPorTipo(@PathVariable String tipo) {
        List<Objeto> objetos = objetoService.buscarPorTipo(tipo);
        return ResponseEntity.ok(objetos);
    }

    // GET /api/objetos/search/tipo/{tipo} - Buscar por tipo contendo
    @GetMapping("/search/tipo/{tipo}")
    public ResponseEntity<List<Objeto>> buscarPorTipoContendo(@PathVariable String tipo) {
        List<Objeto> objetos = objetoService.buscarPorTipoContendo(tipo);
        return ResponseEntity.ok(objetos);
    }

    // GET /api/objetos/status/{status} - Buscar por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Objeto>> buscarPorStatus(@PathVariable String status) {
        List<Objeto> objetos = objetoService.buscarPorStatus(status);
        return ResponseEntity.ok(objetos);
    }

    // GET /api/objetos/ativo/{ativo} - Buscar objetos ativos/inativos
    @GetMapping("/ativo/{ativo}")
    public ResponseEntity<List<Objeto>> buscarPorAtivo(@PathVariable Integer ativo) {
        List<Objeto> objetos = objetoService.buscarPorAtivo(ativo);
        return ResponseEntity.ok(objetos);
    }

    // GET /api/objetos/potencia/{potencia} - Buscar por potência exata
    @GetMapping("/potencia/{potencia}")
    public ResponseEntity<List<Objeto>> buscarPorPotencia(@PathVariable Integer potencia) {
        List<Objeto> objetos = objetoService.buscarPorPotencia(potencia);
        return ResponseEntity.ok(objetos);
    }

    // GET /api/objetos/potencia/{min}/{max} - Buscar por faixa de potência
    @GetMapping("/potencia/{min}/{max}")
    public ResponseEntity<List<Objeto>> buscarPorFaixaPotencia(@PathVariable Integer min,
                                                               @PathVariable Integer max) {
        List<Objeto> objetos = objetoService.buscarPorFaixaPotencia(min, max);
        return ResponseEntity.ok(objetos);
    }

    // GET /api/objetos/potencia/maior/{potencia} - Buscar por potência maior que
    @GetMapping("/potencia/maior/{potencia}")
    public ResponseEntity<List<Objeto>> buscarPorPotenciaMaiorQue(@PathVariable Integer potencia) {
        List<Objeto> objetos = objetoService.buscarPorPotenciaMaiorQue(potencia);
        return ResponseEntity.ok(objetos);
    }

    // GET /api/objetos/tempo-uso/maior/{tempoUso} - Buscar por tempo de uso maior que
    @GetMapping("/tempo-uso/maior/{tempoUso}")
    public ResponseEntity<List<Objeto>> buscarPorTempoUsoMaiorQue(@PathVariable Double tempoUso) {
        List<Objeto> objetos = objetoService.buscarPorTempoUsoMaiorQue(tempoUso);
        return ResponseEntity.ok(objetos);
    }

    // GET /api/objetos/ativo/{ativo}/tipo/{tipo} - Buscar objetos ativos por tipo
    @GetMapping("/ativo/{ativo}/tipo/{tipo}")
    public ResponseEntity<List<Objeto>> buscarPorAtivoETipo(@PathVariable Integer ativo,
                                                            @PathVariable String tipo) {
        List<Objeto> objetos = objetoService.buscarPorAtivoETipo(ativo, tipo);
        return ResponseEntity.ok(objetos);
    }

    // GET /api/objetos/{id}/ambientes - Buscar objeto com ambientes
    @GetMapping("/{id}/ambientes")
    public ResponseEntity<Objeto> buscarPorIdComAmbientes(@PathVariable Integer id) {
        Optional<Objeto> objeto = objetoService.buscarPorIdComAmbientes(id);
        return objeto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/objetos/{id}/completo - Buscar objeto completo
    @GetMapping("/{id}/completo")
    public ResponseEntity<Objeto> buscarPorIdCompleto(@PathVariable Integer id) {
        Optional<Objeto> objeto = objetoService.buscarPorIdCompleto(id);
        return objeto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/objetos/count - Contar objetos
    @GetMapping("/count")
    public ResponseEntity<Long> contar() {
        long count = objetoService.contar();
        return ResponseEntity.ok(count);
    }

    // GET /api/objetos/count/tipo/{tipo} - Contar objetos por tipo
    @GetMapping("/count/tipo/{tipo}")
    public ResponseEntity<Long> contarPorTipo(@PathVariable String tipo) {
        long count = objetoService.contarPorTipo(tipo);
        return ResponseEntity.ok(count);
    }

    // GET /api/objetos/count/status/{status} - Contar objetos por status
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> contarPorStatus(@PathVariable String status) {
        long count = objetoService.contarPorStatus(status);
        return ResponseEntity.ok(count);
    }

    // GET /api/objetos/count/ativo/{ativo} - Contar objetos ativos
    @GetMapping("/count/ativo/{ativo}")
    public ResponseEntity<Long> contarPorAtivo(@PathVariable Integer ativo) {
        long count = objetoService.contarPorAtivo(ativo);
        return ResponseEntity.ok(count);
    }

    // GET /api/objetos/exists/nome/{nome} - Verificar se existe por nome
    @GetMapping("/exists/nome/{nome}")
    public ResponseEntity<Boolean> existePorNome(@PathVariable String nome) {
        boolean existe = objetoService.existePorNome(nome);
        return ResponseEntity.ok(existe);
    }

    // GET /api/objetos/exists/tipo/{tipo} - Verificar se existe por tipo
    @GetMapping("/exists/tipo/{tipo}")
    public ResponseEntity<Boolean> existePorTipo(@PathVariable String tipo) {
        boolean existe = objetoService.existePorTipo(tipo);
        return ResponseEntity.ok(existe);
    }
}
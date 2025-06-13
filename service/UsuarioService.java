package com.ecosmart.eco.service;

import com.ecosmart.eco.model.Usuario;
import com.ecosmart.eco.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // CREATE - Criar usuário
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // READ - Buscar todos
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    // READ - Buscar por ID
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    // READ - Buscar por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // UPDATE - Atualizar usuário
    public Usuario atualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // DELETE - Deletar por ID
    public void deletarPorId(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // DELETE - Deletar usuário
    public void deletar(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    // Verificar se existe
    public boolean existePorId(Integer id) {
        return usuarioRepository.existsById(id);
    }

    // Verificar se email já existe
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Contar usuários
    public long contar() {
        return usuarioRepository.count();
    }

    // STRATEGY PATTERN - TIPO COMPORTAMENTAL
    // Define diferentes estratégias de busca de usuários baseadas em critérios específicos
    // Permite escolher o algoritmo de busca em tempo de execução sem alterar o código cliente

    /**
     * Interface Strategy para diferentes estratégias de busca de usuários
     */
    public interface UsuarioBuscaStrategy {
        List<Usuario> buscar(String criterio);
    }

    /**
     * Strategy concreta: Busca por nome (contém)
     */
    private class BuscaPorNomeStrategy implements UsuarioBuscaStrategy {
        @Override
        public List<Usuario> buscar(String nome) {
            return usuarioRepository.findByNomeContainingIgnoreCase(nome);
        }
    }

    /**
     * Strategy concreta: Busca por nome exato
     */
    private class BuscaPorNomeExatoStrategy implements UsuarioBuscaStrategy {
        @Override
        public List<Usuario> buscar(String nome) {
            return usuarioRepository.findByNome(nome);
        }
    }

    /**
     * Strategy concreta: Busca por nome que começa com
     */
    private class BuscaPorInicioNomeStrategy implements UsuarioBuscaStrategy {
        @Override
        public List<Usuario> buscar(String nome) {
            return usuarioRepository.findByNomeStartingWithIgnoreCase(nome);
        }
    }

    // Context - usa as strategies
    private UsuarioBuscaStrategy buscaStrategy;

    /**
     * Define a estratégia de busca a ser utilizada
     */
    public void setBuscaStrategy(UsuarioBuscaStrategy strategy) {
        this.buscaStrategy = strategy;
    }

    /**
     * Executa a busca usando a estratégia definida
     */
    public List<Usuario> buscarComStrategy(String criterio) {
        if (buscaStrategy == null) {
            // Strategy padrão se nenhuma for definida
            buscaStrategy = new BuscaPorNomeStrategy();
        }
        return buscaStrategy.buscar(criterio);
    }

    // Métodos de conveniência para usar as strategies diretamente
    // Estes são métodos EXTRAS - não afetam os métodos originais

    /**
     * Busca usuários por nome (contém) usando Strategy Pattern
     */
    public List<Usuario> buscarPorNomeComStrategy(String nome) {
        setBuscaStrategy(new BuscaPorNomeStrategy());
        return buscarComStrategy(nome);
    }

    /**
     * Busca usuários por nome exato usando Strategy Pattern
     */
    public List<Usuario> buscarPorNomeExatoComStrategy(String nome) {
        setBuscaStrategy(new BuscaPorNomeExatoStrategy());
        return buscarComStrategy(nome);
    }

    /**
     * Busca usuários por início do nome usando Strategy Pattern
     */
    public List<Usuario> buscarPorInicioNomeComStrategy(String nome) {
        setBuscaStrategy(new BuscaPorInicioNomeStrategy());
        return buscarComStrategy(nome);
    }

    // Enum para facilitar a escolha da estratégia
    public enum TipoBusca {
        NOME_CONTEM,
        NOME_EXATO,
        NOME_INICIA_COM
    }

    /**
     * Método utilitário que aceita enum para escolher a estratégia
     */
    public List<Usuario> buscarPorTipo(String criterio, TipoBusca tipoBusca) {
        switch (tipoBusca) {
            case NOME_EXATO:
                return buscarPorNomeExatoComStrategy(criterio);
            case NOME_INICIA_COM:
                return buscarPorInicioNomeComStrategy(criterio);
            case NOME_CONTEM:
            default:
                return buscarPorNomeComStrategy(criterio);
        }
    }
}
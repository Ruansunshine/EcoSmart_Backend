package com.ecosmart.eco.service;

import com.ecosmart.eco.model.Relatorio;
import com.ecosmart.eco.model.Ambiente;
import com.ecosmart.eco.model.Usuario;
import com.ecosmart.eco.repository.RelatorioRepository;
import com.ecosmart.eco.repository.AmbienteRepository;
import com.ecosmart.eco.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ===== OPERAÇÕES BÁSICAS =====

    /**
     * Salva um relatório (criar ou atualizar)
     */
    public Relatorio salvar(Relatorio relatorio) {
        return relatorioRepository.save(relatorio);
    }

    /**
     * Busca relatório por ID
     */
    @Transactional(readOnly = true)
    public Optional<Relatorio> buscarPorId(Integer id) {
        return relatorioRepository.findById(id);
    }

    /**
     * Busca todos os relatórios
     */
    @Transactional(readOnly = true)
    public List<Relatorio> buscarTodos() {
        return relatorioRepository.findAll();
    }

    /**
     * Deleta relatório por ID
     */
    public void deletarPorId(Integer id) {
        relatorioRepository.deleteById(id);
    }

    /**
     * Deleta relatório
     */
    public void deletar(Relatorio relatorio) {
        relatorioRepository.delete(relatorio);
    }

    /**
     * Conta total de relatórios
     */
    @Transactional(readOnly = true)
    public long contarTodos() {
        return relatorioRepository.count();
    }

    // ===== CONSULTAS POR AMBIENTE =====

    /**
     * Busca relatórios por ambiente
     */
    @Transactional(readOnly = true)
    public List<Relatorio> buscarPorAmbiente(Ambiente ambiente) {
        return relatorioRepository.findByAmbiente(ambiente);
    }

    /**
     * Busca relatórios por ID do ambiente
     */
    @Transactional(readOnly = true)
    public List<Relatorio> buscarPorIdAmbiente(Integer ambienteId) {
        return relatorioRepository.findByAmbiente_IdAmbiente(ambienteId);
    }

    /**
     * Conta relatórios por ambiente
     */
    @Transactional(readOnly = true)
    public Long contarPorAmbiente(Integer ambienteId) {
        return relatorioRepository.countByAmbienteId(ambienteId);
    }

    // ===== CONSULTAS POR USUÁRIO =====

    /**
     * Busca relatórios por usuário
     */
    @Transactional(readOnly = true)
    public List<Relatorio> buscarPorUsuario(Usuario usuario) {
        return relatorioRepository.findByUsuario(usuario);
    }

    /**
     * Busca relatórios por ID do usuário
     */
    @Transactional(readOnly = true)
    public List<Relatorio> buscarPorIdUsuario(Integer usuarioId) {
        return relatorioRepository.findByUsuarioId(usuarioId);
    }

    /**
     * Conta relatórios por usuário
     */
    @Transactional(readOnly = true)
    public Long contarPorUsuario(Integer usuarioId) {
        return relatorioRepository.countByUsuarioId(usuarioId);
    }

    // ===== CONSULTAS COMBINADAS =====

    /**
     * Busca relatórios por ambiente e usuário
     */
    @Transactional(readOnly = true)
    public List<Relatorio> buscarPorAmbienteEUsuario(Ambiente ambiente, Usuario usuario) {
        return relatorioRepository.findByAmbienteAndUsuario(ambiente, usuario);
    }

    /**
     * Busca relatórios por ID do ambiente e ID do usuário
     */
    @Transactional(readOnly = true)
    public List<Relatorio> buscarPorIdAmbienteEIdUsuario(Integer ambienteId, Integer usuarioId) {
        return relatorioRepository.findByAmbienteIdAndUsuarioId(ambienteId, usuarioId);
    }

    // ===== VERIFICAÇÕES DE EXISTÊNCIA =====

    /**
     * Verifica se existe relatório para ambiente e usuário
     */
    @Transactional(readOnly = true)
    public boolean existePorAmbienteEUsuario(Ambiente ambiente, Usuario usuario) {
        return relatorioRepository.existsByAmbienteAndUsuario(ambiente, usuario);
    }

    /**
     * Verifica se existe relatório por ID do ambiente e ID do usuário
     */
    @Transactional(readOnly = true)
    public boolean existePorIdAmbienteEIdUsuario(Integer ambienteId, Integer usuarioId) {
        return relatorioRepository.existsByAmbienteIdAndUsuarioId(ambienteId, usuarioId);
    }

    // ===== CONSULTAS COM DETALHES =====

    /**
     * Busca relatório por ID com detalhes (ambiente e usuário)
     */
    @Transactional(readOnly = true)
    public Optional<Relatorio> buscarPorIdComDetalhes(Integer id) {
        return relatorioRepository.findByIdWithDetails(id);
    }

    // ===== MÉTODOS DE NEGÓCIO =====

    /**
     * Cria um novo relatório
     */
    public Relatorio criarRelatorio(Integer ambienteId, Integer usuarioId, String dadosRelatorio) {
        Optional<Ambiente> ambiente = ambienteRepository.findById(ambienteId);
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);

        if (ambiente.isEmpty()) {
            throw new RuntimeException("Ambiente não encontrado com ID: " + ambienteId);
        }

        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com ID: " + usuarioId);
        }

        Relatorio relatorio = new Relatorio();
        relatorio.setAmbiente(ambiente.get());
        relatorio.setUsuario(usuario.get());
        // Assumindo que existe um campo dadosRelatorio na entidade
        // relatorio.setDadosRelatorio(dadosRelatorio);

        return salvar(relatorio);
    }

    /**
     * Atualiza um relatório existente
     */
    public Relatorio atualizarRelatorio(Integer id, String novosDados) {
        Optional<Relatorio> relatorioExistente = buscarPorId(id);

        if (relatorioExistente.isEmpty()) {
            throw new RuntimeException("Relatório não encontrado com ID: " + id);
        }

        Relatorio relatorio = relatorioExistente.get();
        // Assumindo que existe um campo dadosRelatorio na entidade
        // relatorio.setDadosRelatorio(novosDados);

        return salvar(relatorio);
    }

    /**
     * Verifica se usuário tem acesso ao relatório
     */
    @Transactional(readOnly = true)
    public boolean usuarioTemAcessoAoRelatorio(Integer relatorioId, Integer usuarioId) {
        Optional<Relatorio> relatorio = buscarPorId(relatorioId);

        if (relatorio.isEmpty()) {
            return false;
        }

        return relatorio.get().getUsuario().getId_usuario().equals(usuarioId);
    }

    /**
     * Busca relatórios recentes de um usuário (assumindo que existe campo dataGeracao)
     */
    @Transactional(readOnly = true)
    public List<Relatorio> buscarRelatoriosRecentesDoUsuario(Integer usuarioId) {
        // Esta implementação dependeria de ter um campo de data na entidade Relatorio
        // Por enquanto, retorna todos os relatórios do usuário
        return buscarPorIdUsuario(usuarioId);
    }

    /**
     * Remove todos os relatórios de um ambiente
     */
    public void removerRelatoriosDoAmbiente(Integer ambienteId) {
        List<Relatorio> relatorios = buscarPorIdAmbiente(ambienteId);
        relatorios.forEach(this::deletar);
    }

    /**
     * Remove todos os relatórios de um usuário
     */
    public void removerRelatoriosDoUsuario(Integer usuarioId) {
        List<Relatorio> relatorios = buscarPorIdUsuario(usuarioId);
        relatorios.forEach(this::deletar);
    }
}
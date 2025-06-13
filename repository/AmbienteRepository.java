package com.ecosmart.eco.repository;

import com.ecosmart.eco.model.Ambiente;
import com.ecosmart.eco.model.Objeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AmbienteRepository extends JpaRepository<Ambiente, Integer> {

    // ============================================================
    // MÉTODOS BÁSICOS DO JpaRepository (já incluídos automaticamente):
    // save(Ambiente ambiente) - Salvar/Atualizar
    // findById(Integer id) - Buscar por ID
    // findAll() - Buscar todos
    // deleteById(Integer id) - Deletar por ID
    // delete(Ambiente ambiente) - Deletar entidade
    // count() - Contar registros
    // ============================================================

    // ============================================================
    // CONSULTAS POR CAMPOS DIRETOS DA ENTIDADE AMBIENTE
    // ============================================================

    /**
     * Busca ambientes por nome exato
     * Padrão Query Method - Spring interpreta automaticamente
     */
    List<Ambiente> findByNome(String nome);

    /**
     * Busca ambientes por nome (contém texto, ignora case)
     * Padrão Query Method - Spring interpreta automaticamente
     */
    List<Ambiente> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca ambientes por nome que começam com determinado texto
     * Padrão Query Method - Spring interpreta automaticamente
     */
    List<Ambiente> findByNomeStartingWithIgnoreCase(String nome);

    /**
     * Busca ambientes por descrição contendo texto
     * Padrão Query Method - Spring interpreta automaticamente
     */
    List<Ambiente> findByDescricaoContainingIgnoreCase(String descricao);

    /**
     * Verifica se existe ambiente com o nome
     * Padrão Query Method - Spring interpreta automaticamente
     */
    boolean existsByNome(String nome);

    // ============================================================
    // CONSULTAS POR RELACIONAMENTOS (ONE-TO-MANY: objetos)
    // ============================================================

    /**
     * Busca ambientes que contêm determinado objeto
     * Usa relacionamento OneToMany com objetos
     */
    @Query("SELECT a FROM Ambiente a JOIN a.objetos o WHERE o = :objeto")
    List<Ambiente> findByObjeto(@Param("objeto") Objeto objeto);

    /**
     * Busca ambientes por ID do objeto
     * Usa relacionamento OneToMany com objetos
     */
    @Query("SELECT a FROM Ambiente a JOIN a.objetos o WHERE o.idObjeto = :objetoId")
    List<Ambiente> findByObjetoId(@Param("objetoId") Integer objetoId);

    /**
     * Verifica se existe ambiente que contém o objeto
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Ambiente a JOIN a.objetos o WHERE o = :objeto")
    boolean existsByObjeto(@Param("objeto") Objeto objeto);

    /**
     * Verifica se existe ambiente para o ID do objeto
     */
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Ambiente a JOIN a.objetos o WHERE o.idObjeto = :objetoId")
    boolean existsByObjetoId(@Param("objetoId") Integer objetoId);

    /**
     * Conta ambientes que contêm determinado objeto
     */
    @Query("SELECT COUNT(a) FROM Ambiente a JOIN a.objetos o WHERE o = :objeto")
    long countByObjeto(@Param("objeto") Objeto objeto);

    /**
     * Conta ambientes por ID do objeto
     */
    @Query("SELECT COUNT(a) FROM Ambiente a JOIN a.objetos o WHERE o.idObjeto = :objetoId")
    long countByObjetoId(@Param("objetoId") Integer objetoId);

    // ============================================================
    // CONSULTAS POR PROPRIEDADES DOS OBJETOS RELACIONADOS
    // ============================================================

    /**
     * Query customizada para buscar ambientes por tipo de objeto
     */
    @Query("SELECT DISTINCT a FROM Ambiente a JOIN a.objetos o WHERE o.tipoObjeto = :tipoObjeto")
    List<Ambiente> findByObjetoTipoObjeto(@Param("tipoObjeto") String tipoObjeto);

    /**
     * Query customizada para buscar ambientes por nome do objeto
     */
    @Query("SELECT DISTINCT a FROM Ambiente a JOIN a.objetos o WHERE o.nomeObjeto = :nomeObjeto")
    List<Ambiente> findByObjetoNomeObjeto(@Param("nomeObjeto") String nomeObjeto);

    /**
     * Query customizada para buscar ambientes por status do objeto
     */
    @Query("SELECT DISTINCT a FROM Ambiente a JOIN a.objetos o WHERE o.status = :status")
    List<Ambiente> findByObjetoStatus(@Param("status") String status);

    /**
     * Query customizada para buscar ambientes com objetos ativos
     */
    @Query("SELECT DISTINCT a FROM Ambiente a JOIN a.objetos o WHERE o.ativo = :ativo")
    List<Ambiente> findByObjetoAtivo(@Param("ativo") Integer ativo);

    /**
     * Query customizada para buscar ambientes com objetos de potência maior que um valor
     */
    @Query("SELECT DISTINCT a FROM Ambiente a JOIN a.objetos o WHERE o.potencia > :potencia")
    List<Ambiente> findByObjetoPotenciaGreaterThan(@Param("potencia") Integer potencia);

    /**
     * Query customizada para buscar ambientes por faixa de potência do objeto
     */
    @Query("SELECT DISTINCT a FROM Ambiente a JOIN a.objetos o WHERE o.potencia BETWEEN :potenciaMin AND :potenciaMax")
    List<Ambiente> findByObjetoPotenciaBetween(@Param("potenciaMin") Integer potenciaMin, @Param("potenciaMax") Integer potenciaMax);

    /**
     * Query customizada para contar ambientes por tipo de objeto
     */
    @Query("SELECT COUNT(DISTINCT a) FROM Ambiente a JOIN a.objetos o WHERE o.tipoObjeto = :tipoObjeto")
    long countByObjetoTipoObjeto(@Param("tipoObjeto") String tipoObjeto);

    /**
     * Query customizada para contar ambientes com objetos ativos
     */
    @Query("SELECT COUNT(DISTINCT a) FROM Ambiente a JOIN a.objetos o WHERE o.ativo = :ativo")
    long countByObjetoAtivo(@Param("ativo") Integer ativo);

    // ============================================================
    // CONSULTAS COM FETCH JOIN (PADRÃO EAGER LOADING SELETIVO)
    // Otimiza performance ao carregar relacionamentos específicos
    // ============================================================

    /**
     * Query customizada para buscar ambiente com seus objetos (FETCH JOIN)
     * Padrão Eager Loading Seletivo - carrega objetos junto com o ambiente
     */
    @Query("SELECT DISTINCT a FROM Ambiente a LEFT JOIN FETCH a.objetos WHERE a.idAmbiente = :id")
    Optional<Ambiente> findByIdWithObjetos(@Param("id") Integer id);

    /**
     * Query customizada para buscar ambiente com seus usuários (FETCH JOIN)
     * Padrão Eager Loading Seletivo - carrega usuários junto com o ambiente
     */
    @Query("SELECT DISTINCT a FROM Ambiente a LEFT JOIN FETCH a.usuarios WHERE a.idAmbiente = :id")
    Optional<Ambiente> findByIdWithUsuarios(@Param("id") Integer id);

    /**
     * Query customizada para buscar ambiente com seus relatórios (FETCH JOIN)
     * Padrão Eager Loading Seletivo - carrega relatórios junto com o ambiente
     */
    @Query("SELECT DISTINCT a FROM Ambiente a LEFT JOIN FETCH a.relatorios WHERE a.idAmbiente = :id")
    Optional<Ambiente> findByIdWithRelatorios(@Param("id") Integer id);

    /**
     * Query customizada para buscar ambiente completo (com todas as relações)
     * ATENÇÃO: Use com cuidado - pode impactar performance com muitos dados
     * Padrão Eager Loading Seletivo - carrega todos os relacionamentos
     */
    @Query("SELECT DISTINCT a FROM Ambiente a " +
            "LEFT JOIN FETCH a.objetos " +
            "LEFT JOIN FETCH a.usuarios " +
            "LEFT JOIN FETCH a.relatorios " +
            "WHERE a.idAmbiente = :id")
    Optional<Ambiente> findByIdComplete(@Param("id") Integer id);

    // ============================================================
    // CONSULTAS POR RELACIONAMENTOS (MANY-TO-MANY: usuarios)
    // ============================================================

    /**
     * Busca ambientes por usuário específico
     */
    @Query("SELECT DISTINCT a FROM Ambiente a JOIN a.usuarios u WHERE u.id_usuario = :usuarioId")
    List<Ambiente> findByUsuarioId(@Param("usuarioId") Integer usuarioId);

    /**
     * Busca ambientes por nome do usuário
     */
    @Query("SELECT DISTINCT a FROM Ambiente a JOIN a.usuarios u WHERE u.nome = :nomeUsuario")
    List<Ambiente> findByUsuarioNome(@Param("nomeUsuario") String nomeUsuario);

    /**
     * Conta ambientes por usuário
     */
    @Query("SELECT COUNT(DISTINCT a) FROM Ambiente a JOIN a.usuarios u WHERE u.id_usuario = :usuarioId")
    long countByUsuarioId(@Param("usuarioId") Integer usuarioId);

    // ============================================================
    // CONSULTAS AVANÇADAS COM MÚLTIPLAS CONDIÇÕES
    // ============================================================

    /**
     * Busca ambientes por nome e tipo de objeto
     */
    @Query("SELECT DISTINCT a FROM Ambiente a JOIN a.objetos o WHERE a.nome = :nomeAmbiente AND o.tipoObjeto = :tipoObjeto")
    List<Ambiente> findByNomeAndObjetoTipo(@Param("nomeAmbiente") String nomeAmbiente, @Param("tipoObjeto") String tipoObjeto);

    /**
     * Busca ambientes com objetos ativos de determinado tipo
     */
    @Query("SELECT DISTINCT a FROM Ambiente a JOIN a.objetos o WHERE o.ativo = 1 AND o.tipoObjeto = :tipoObjeto")
    List<Ambiente> findByObjetoAtivoAndTipo(@Param("tipoObjeto") String tipoObjeto);

    /**
     * Busca ambientes por usuário e com objetos ativos
     */
    @Query("SELECT DISTINCT a FROM Ambiente a " +
            "JOIN a.usuarios u " +
            "JOIN a.objetos o " +
            "WHERE u.id_usuario = :usuarioId AND o.ativo = 1")
    List<Ambiente> findByUsuarioIdAndObjetosAtivos(@Param("usuarioId") Integer usuarioId);

    // ============================================================
    // CONSULTAS DE ESTATÍSTICAS E RELATÓRIOS
    // ============================================================

    /**
     * Conta total de objetos por ambiente
     */
    @Query("SELECT a.nome, COUNT(o) FROM Ambiente a LEFT JOIN a.objetos o GROUP BY a.idAmbiente, a.nome")
    List<Object[]> countObjetosByAmbiente();

    /**
     * Busca ambientes com mais de X objetos
     */
    @Query("SELECT a FROM Ambiente a WHERE SIZE(a.objetos) > :quantidade")
    List<Ambiente> findAmbientesWithMoreThanXObjetos(@Param("quantidade") int quantidade);

    /**
     * Busca ambientes sem objetos
     */
    @Query("SELECT a FROM Ambiente a WHERE SIZE(a.objetos) = 0")
    List<Ambiente> findAmbientesSemObjetos();

    /**
     * Busca ambientes sem usuários associados
     */
    @Query("SELECT a FROM Ambiente a WHERE SIZE(a.usuarios) = 0")
    List<Ambiente> findAmbientesSemUsuarios();
}
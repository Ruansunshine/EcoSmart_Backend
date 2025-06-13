package com.ecosmart.eco.repository;

import com.ecosmart.eco.model.Objeto;
import com.ecosmart.eco.model.Ambiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObjetoRepository extends JpaRepository<Objeto, Integer> {

    // Métodos básicos já incluídos pelo JpaRepository:
    // save(Objeto objeto) - Salvar/Atualizar
    // findById(Integer id) - Buscar por ID
    // findAll() - Buscar todos
    // deleteById(Integer id) - Deletar por ID
    // delete(Objeto objeto) - Deletar entidade
    // count() - Contar registros

    // Métodos personalizados para consultas específicas

    /**
     * Busca objetos por nome
     */
    List<Objeto> findByNomeObjeto(String nomeObjeto);

    /**
     * Busca objetos por nome (contém)
     */
    List<Objeto> findByNomeObjetoContainingIgnoreCase(String nomeObjeto);

    /**
     * Busca objetos por tipo
     */
    List<Objeto> findByTipoObjeto(String tipoObjeto);

    /**
     * Busca objetos por tipo (contém)
     */
    List<Objeto> findByTipoObjetoContainingIgnoreCase(String tipoObjeto);

    /**
     * Busca objetos por status
     */
    List<Objeto> findByStatus(String status);

    /**
     * Busca objetos ativos
     */
    List<Objeto> findByAtivo(Integer ativo);

    /**
     * Busca objetos por potência
     */
    List<Objeto> findByPotencia(Integer potencia);

    /**
     * Busca objetos por faixa de potência
     */
    List<Objeto> findByPotenciaBetween(Integer potenciaMin, Integer potenciaMax);

    /**
     * Verifica se existe objeto com o nome
     */
    boolean existsByNomeObjeto(String nomeObjeto);

    /**
     * Verifica se existe objeto do tipo
     */
    boolean existsByTipoObjeto(String tipoObjeto);

    /**
     * Conta objetos por tipo
     */
    long countByTipoObjeto(String tipoObjeto);

    /**
     * Conta objetos por status
     */
    long countByStatus(String status);

    /**
     * Conta objetos ativos
     */
    long countByAtivo(Integer ativo);

    /**
     * Query customizada para buscar objeto com seu ambiente
     */
    @Query("SELECT DISTINCT o FROM Objeto o LEFT JOIN FETCH o.ambiente WHERE o.idObjeto = :id")
    Optional<Objeto> findByIdWithAmbientes(@Param("id") Integer id);

    /**
     * Query customizada para buscar objeto completo (com todas as relações)
     */
    @Query("SELECT DISTINCT o FROM Objeto o " +
            "LEFT JOIN FETCH o.ambiente " +
            "WHERE o.idObjeto = :id")
    Optional<Objeto> findByIdComplete(@Param("id") Integer id);

    /**
     * Busca objetos por nome que começam com determinado texto
     */
    List<Objeto> findByNomeObjetoStartingWithIgnoreCase(String nomeObjeto);

    /**
     * Busca objetos por tipo que começam com determinado texto
     */
    List<Objeto> findByTipoObjetoStartingWithIgnoreCase(String tipoObjeto);

    /**
     * Query customizada para buscar objetos com potência maior que um valor
     */
    @Query("SELECT o FROM Objeto o WHERE o.potencia > :potencia")
    List<Objeto> findByPotenciaGreaterThan(@Param("potencia") Integer potencia);

    /**
     * Query customizada para buscar objetos com tempo de uso maior que um valor
     */
    @Query("SELECT o FROM Objeto o WHERE o.tempoUso > :tempoUso")
    List<Objeto> findByTempoUsoGreaterThan(@Param("tempoUso") Double tempoUso);

    /**
     * Query customizada para buscar objetos ativos por tipo
     */
    @Query("SELECT o FROM Objeto o WHERE o.ativo = :ativo AND o.tipoObjeto = :tipoObjeto")
    List<Objeto> findByAtivoAndTipoObjeto(@Param("ativo") Integer ativo, @Param("tipoObjeto") String tipoObjeto);
}
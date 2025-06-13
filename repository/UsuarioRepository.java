package com.ecosmart.eco.repository;

import com.ecosmart.eco.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Métodos básicos já incluídos pelo JpaRepository:
    // save(Usuario usuario) - Salvar/Atualizar
    // findById(Integer id) - Buscar por ID
    // findAll() - Buscar todos
    // deleteById(Integer id) - Deletar por ID
    // delete(Usuario usuario) - Deletar entidade
    // count() - Contar registros

    // Métodos personalizados para consultas específicas

    /**
     * Busca usuário por email
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Busca usuários por nome (contém)
     */
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    /**
     * Verifica se existe usuário com o email
     */
    boolean existsByEmail(String email);

    /**
     * Busca usuários por nome exato
     */
    List<Usuario> findByNome(String nome);

    /**
     * Busca usuários que começam com determinado nome
     */
    List<Usuario> findByNomeStartingWithIgnoreCase(String nome);

    /**
     * Query customizada para buscar usuários com seus ambientes
     */
    @Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.ambientes WHERE u.id_usuario = :id")
    Optional<Usuario> findByIdWithAmbientes(@Param("id") Integer id);

    /**
     * Query customizada para buscar usuários com seus relatórios
     */
    @Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.relatorios WHERE u.id_usuario = :id")
    Optional<Usuario> findByIdWithRelatorios(@Param("id") Integer id);

    /**
     * Query customizada para buscar usuário completo (com ambientes e relatórios)
     */
    @Query("SELECT DISTINCT u FROM Usuario u " +
            "LEFT JOIN FETCH u.ambientes " +
            "LEFT JOIN FETCH u.relatorios " +
            "WHERE u.id_usuario = :id")
    Optional<Usuario> findByIdComplete(@Param("id") Integer id);

    /**
     * Busca usuários por email e senha (para login)
     * Nota: Em produção, use hash da senha!
     */
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}
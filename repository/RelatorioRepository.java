package com.ecosmart.eco.repository;

import com.ecosmart.eco.model.Relatorio;
import com.ecosmart.eco.model.Ambiente;
import com.ecosmart.eco.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Integer> {

    // Buscar relatórios por ambiente
    List<Relatorio> findByAmbiente(Ambiente ambiente);

    // Buscar relatórios por usuário
    List<Relatorio> findByUsuario(Usuario usuario);

    // Buscar relatórios por ID do ambiente - USANDO @Query
    @Query("SELECT r FROM Relatorio r WHERE r.ambiente.idAmbiente = :ambienteId")
    List<Relatorio> findByAmbienteId(@Param("ambienteId") Integer ambienteId);

    // Buscar relatórios por ID do usuário - USANDO @Query
    @Query("SELECT r FROM Relatorio r WHERE r.usuario.id_usuario = :usuarioId")
    List<Relatorio> findByUsuarioId(@Param("usuarioId") Integer usuarioId);

    // Buscar relatórios por ambiente e usuário
    List<Relatorio> findByAmbienteAndUsuario(Ambiente ambiente, Usuario usuario);

    // Buscar relatórios por ID do ambiente e ID do usuário - USANDO @Query
    @Query("SELECT r FROM Relatorio r WHERE r.ambiente.idAmbiente = :ambienteId AND r.usuario.id_usuario = :usuarioId")
    List<Relatorio> findByAmbienteIdAndUsuarioId(@Param("ambienteId") Integer ambienteId, @Param("usuarioId") Integer usuarioId);

    // Query customizada para buscar relatórios com detalhes
    @Query("SELECT r FROM Relatorio r " +
            "JOIN FETCH r.ambiente a " +
            "JOIN FETCH r.usuario u " +
            "WHERE r.idRelatorio = :id")
    Optional<Relatorio> findByIdWithDetails(@Param("id") Integer id);

    // Query para contar relatórios por ambiente
    @Query("SELECT COUNT(r) FROM Relatorio r WHERE r.ambiente.idAmbiente = :ambienteId")
    Long countByAmbienteId(@Param("ambienteId") Integer ambienteId);

    // Query para contar relatórios por usuário
    @Query("SELECT COUNT(r) FROM Relatorio r WHERE r.usuario.id_usuario = :usuarioId")
    Long countByUsuarioId(@Param("usuarioId") Integer usuarioId);

    // Verificar se existe relatório para um ambiente e usuário específicos
    boolean existsByAmbienteAndUsuario(Ambiente ambiente, Usuario usuario);

    // Verificar se existe relatório por IDs - USANDO @Query
    @Query("SELECT COUNT(r) > 0 FROM Relatorio r WHERE r.ambiente.idAmbiente = :ambienteId AND r.usuario.id_usuario = :usuarioId")
    boolean existsByAmbienteIdAndUsuarioId(@Param("ambienteId") Integer ambienteId, @Param("usuarioId") Integer usuarioId);

    List<Relatorio> findByAmbiente_IdAmbiente(Integer ambienteId);
}
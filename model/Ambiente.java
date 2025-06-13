package com.ecosmart.eco.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "ambiente") // nome da tabela em minúsculo
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ambiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ambiente") // ou o nome real da coluna ID
    private Integer idAmbiente;

    @Column(name = "nome", length = 100)
    private String nome;

    // NOVO CAMPO: Descrição do ambiente
    @Column(name = "descricao", length = 500)
    private String descricao;

    // Relacionamento One-to-Many com Objeto - CORRIGIDO LOGICAMENTE
    @OneToMany(mappedBy = "ambiente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Objeto> objetos;

    // Relacionamento Many-to-Many com Usuario
    @ManyToMany(mappedBy = "ambientes", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Usuario> usuarios;

    // Relacionamento One-to-Many com Relatorio
    @OneToMany(mappedBy = "ambiente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Relatorio> relatorios;

    // ============================================================
    // PADRÃO ESTRUTURAL: FACADE (Fachada) - USO OPCIONAL
    // ============================================================
    // Simplifica operações complexas que envolvem múltiplos relacionamentos
    // Este padrão não afeta o fluxo MVC, apenas oferece uma interface simplificada

    /**
     * Método Facade: Obtém informações completas do ambiente de forma simplificada
     * Encapsula a complexidade de acessar múltiplos relacionamentos
     * @return String com resumo completo do ambiente
     */
    public String obterResumoCompleto() {
        StringBuilder resumo = new StringBuilder();
        resumo.append("Ambiente: ").append(nome);

        if (descricao != null && !descricao.isEmpty()) {
            resumo.append(" - ").append(descricao);
        }

        if (objetos != null && !objetos.isEmpty()) {
            resumo.append(" | Objetos: ").append(objetos.size());
        }

        if (usuarios != null && !usuarios.isEmpty()) {
            resumo.append(" | Usuários: ").append(usuarios.size());
        }

        if (relatorios != null && !relatorios.isEmpty()) {
            resumo.append(" | Relatórios: ").append(relatorios.size());
        }

        return resumo.toString();
    }

    /**
     * Método Facade: Verifica se o ambiente está completamente configurado
     * Simplifica validações complexas em uma única chamada
     * @return true se o ambiente possui todas as informações básicas
     */
    public boolean isAmbienteCompleto() {
        return nome != null && !nome.trim().isEmpty() &&
                descricao != null && !descricao.trim().isEmpty();
    }

    // Constructor para ambiente com descrição
    public Ambiente(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters e Setters manuais (opcionais com Lombok @Data)
    public Integer getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(Integer idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Objeto> getObjetos() {
        return objetos;
    }

    public void setObjetos(List<Objeto> objetos) {
        this.objetos = objetos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Relatorio> getRelatorios() {
        return relatorios;
    }

    public void setRelatorios(List<Relatorio> relatorios) {
        this.relatorios = relatorios;
    }
}
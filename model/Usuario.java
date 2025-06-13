package com.ecosmart.eco.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id_usuario;

    @Column(name = "nome", length = 45, nullable = false)
    private String nome;

    @Column(name = "email", length = 45, nullable = false)
    private String email;

    @Column(name = "senha", length = 45, nullable = false)
    private String senha;

    // Relacionamento Many-to-Many com Ambiente
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "usuario_ambiente",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "ambiente_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Ambiente> ambientes;


    // Relacionamento One-to-Many com Relatorio
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Relatorio> relatorios;

    // PADRÃO BUILDER - TIPO CRIACIONAL
    // Permite criar objetos Usuario de forma mais legível e flexível
    // Útil quando há muitos parâmetros opcionais ou construção complexa
    public static class UsuarioBuilder {
        private String nome;
        private String email;
        private String senha;
        private List<Ambiente> ambientes;
        private List<Relatorio> relatorios;

        // Método para definir nome - retorna o próprio builder (fluent interface)
        public UsuarioBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        // Método para definir email - retorna o próprio builder
        public UsuarioBuilder email(String email) {
            this.email = email;
            return this;
        }

        // Método para definir senha - retorna o próprio builder
        public UsuarioBuilder senha(String senha) {
            this.senha = senha;
            return this;
        }

        // Método para definir ambientes - retorna o próprio builder
        public UsuarioBuilder ambientes(List<Ambiente> ambientes) {
            this.ambientes = ambientes;
            return this;
        }

        // Método para definir relatórios - retorna o próprio builder
        public UsuarioBuilder relatorios(List<Relatorio> relatorios) {
            this.relatorios = relatorios;
            return this;
        }

        // Método build() - constrói e retorna o objeto Usuario final
        public Usuario build() {
            Usuario usuario = new Usuario();
            usuario.nome = this.nome;
            usuario.email = this.email;
            usuario.senha = this.senha;
            usuario.ambientes = this.ambientes;
            usuario.relatorios = this.relatorios;
            return usuario;
        }
    }

    // Método estático para iniciar o builder - ponto de entrada
    public static UsuarioBuilder builder() {
        return new UsuarioBuilder();
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Ambiente> getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(List<Ambiente> ambientes) {
        this.ambientes = ambientes;
    }

    public List<Relatorio> getRelatorios() {
        return relatorios;
    }

    public void setRelatorios(List<Relatorio> relatorios) {
        this.relatorios = relatorios;
    }
}
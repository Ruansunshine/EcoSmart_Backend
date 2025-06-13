package com.ecosmart.eco.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "objeto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Objeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idObjeto")
    private Integer idObjeto;

    @Column(name = "TipoObjeto", length = 45)
    private String tipoObjeto;

    @Column(name = "TempoUso")
    private Double tempoUso;

    @Column(name = "Potencia")
    private Integer potencia;

    @Column(name = "NomeObjeto", length = 45, nullable = false)
    private String nomeObjeto;

    @Column(name = "Status", length = 45)
    private String status;

    @Column(name = "Ativo")
    private Integer ativo;

    // Relacionamento Many-to-One com Ambiente - CORRIGIDO LOGICAMENTE
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ambiente_id", nullable = true)
    @ToString.Exclude
    private Ambiente ambiente;

    // FACTORY METHOD PATTERN - TIPO CRIACIONAL
    // Cria objetos pré-configurados para diferentes tipos de dispositivos
    // Facilita a criação de objetos com configurações específicas por categoria

    // Factory Method para criar Lâmpada
    public static Objeto criarLampada(String nome, Integer potencia) {
        Objeto objeto = new Objeto();
        objeto.setTipoObjeto("LAMPADA");
        objeto.setNomeObjeto(nome);
        objeto.setPotencia(potencia);
        objeto.setStatus("DESLIGADO");
        objeto.setAtivo(1);
        objeto.setTempoUso(0.0);
        return objeto;
    }

    // Factory Method para criar Ar Condicionado
    public static Objeto criarArCondicionado(String nome, Integer potencia) {
        Objeto objeto = new Objeto();
        objeto.setTipoObjeto("AR_CONDICIONADO");
        objeto.setNomeObjeto(nome);
        objeto.setPotencia(potencia);
        objeto.setStatus("DESLIGADO");
        objeto.setAtivo(1);
        objeto.setTempoUso(0.0);
        return objeto;
    }

    // Factory Method para criar Ventilador
    public static Objeto criarVentilador(String nome, Integer potencia) {
        Objeto objeto = new Objeto();
        objeto.setTipoObjeto("VENTILADOR");
        objeto.setNomeObjeto(nome);
        objeto.setPotencia(potencia);
        objeto.setStatus("DESLIGADO");
        objeto.setAtivo(1);
        objeto.setTempoUso(0.0);
        return objeto;
    }

    // Factory Method para criar Televisão
    public static Objeto criarTelevisao(String nome, Integer potencia) {
        Objeto objeto = new Objeto();
        objeto.setTipoObjeto("TELEVISAO");
        objeto.setNomeObjeto(nome);
        objeto.setPotencia(potencia);
        objeto.setStatus("DESLIGADO");
        objeto.setAtivo(1);
        objeto.setTempoUso(0.0);
        return objeto;
    }

    // Factory Method para criar Geladeira
    public static Objeto criarGeladeira(String nome, Integer potencia) {
        Objeto objeto = new Objeto();
        objeto.setTipoObjeto("GELADEIRA");
        objeto.setNomeObjeto(nome);
        objeto.setPotencia(potencia);
        objeto.setStatus("LIGADO"); // Geladeira geralmente fica sempre ligada
        objeto.setAtivo(1);
        objeto.setTempoUso(0.0);
        return objeto;
    }

    // Factory Method genérico para outros tipos
    public static Objeto criarEletrodomestico(String nome, String tipo, Integer potencia) {
        Objeto objeto = new Objeto();
        objeto.setTipoObjeto(tipo.toUpperCase());
        objeto.setNomeObjeto(nome);
        objeto.setPotencia(potencia);
        objeto.setStatus("DESLIGADO");
        objeto.setAtivo(1);
        objeto.setTempoUso(0.0);
        return objeto;
    }

    public Integer getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(Integer idObjeto) {
        this.idObjeto = idObjeto;
    }

    public String getTipoObjeto() {
        return tipoObjeto;
    }

    public void setTipoObjeto(String tipoObjeto) {
        this.tipoObjeto = tipoObjeto;
    }

    public Double getTempoUso() {
        return tempoUso;
    }

    public void setTempoUso(Double tempoUso) {
        this.tempoUso = tempoUso;
    }

    public Integer getPotencia() {
        return potencia;
    }

    public void setPotencia(Integer potencia) {
        this.potencia = potencia;
    }

    public String getNomeObjeto() {
        return nomeObjeto;
    }

    public void setNomeObjeto(String nomeObjeto) {
        this.nomeObjeto = nomeObjeto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }
}
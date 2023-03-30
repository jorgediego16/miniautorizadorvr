package com.vr.miniautorizador.domain.dto;

public class TransacaoDTO {

    private String numeroCartao;

    private String senhaCartao;

    private Double valor;

    public TransacaoDTO(){}

    public TransacaoDTO(String numeroCartao, String senhaCartao, Double valor) {
        this.numeroCartao = numeroCartao;
        this.senhaCartao = senhaCartao;
        this.valor = valor;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getSenhaCartao() {
        return senhaCartao;
    }

    public void setSenhaCartao(String senhaCartao) {
        this.senhaCartao = senhaCartao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}

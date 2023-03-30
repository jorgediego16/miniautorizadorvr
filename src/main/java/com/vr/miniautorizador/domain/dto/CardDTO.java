package com.vr.miniautorizador.domain.dto;

/**
 * DTO contendo os dados do cartão
 */
public class CardDTO {
    /**
     * Numero do cartão
     */
    private String numeroCartao;

    /**
     * Senha do cartão
     */
    private String senha;

    private Double saldo;

    public CardDTO(String numeroCartao, String senha, Double saldo ) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = saldo;
    }

    public CardDTO(){}

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}

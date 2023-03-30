package com.vr.miniautorizador.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * Collection onde serao armazenados os cartoes
 */
@Document(collection = "cards")
public class Card {

    /**
     * Chave primaria da collection
     */
    @MongoId
    private String id;

    /**
     * Numero do cartão
     */
    private String numeroCartao;

    /**
     * Senha do cartão
     */
    private String senha;

    private Double saldo;

    public Card(String numeroCartao, String senha, Double saldo) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = saldo;
    }

    public Card() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

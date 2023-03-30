package com.vr.miniautorizador.domain.enums;

public enum TransactionErros {
    SALDO_INSUFICIENTE("SALDO_INSUFICIENTE"),
    SENHA_INVALIDA("SENHA_INVALIDA"),
    CARTAO_INEXISTENTE("CARTAO_INEXISTENTE");

    TransactionErros(String mensagem) {}
}

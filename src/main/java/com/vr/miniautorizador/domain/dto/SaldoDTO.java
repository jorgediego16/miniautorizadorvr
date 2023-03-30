package com.vr.miniautorizador.domain.dto;

public class SaldoDTO {
    private Double saldo;

    public SaldoDTO(Double saldo) {
        this.saldo = saldo;
    }

    public Double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}

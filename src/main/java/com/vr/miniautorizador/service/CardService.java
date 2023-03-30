package com.vr.miniautorizador.service;

import com.vr.miniautorizador.domain.dto.CardDTO;
import com.vr.miniautorizador.domain.dto.SaldoDTO;
import com.vr.miniautorizador.domain.dto.TransacaoDTO;
import com.vr.miniautorizador.domain.enums.TransactionErros;

import java.util.Optional;

public interface CardService {
    CardDTO save(CardDTO cardDTO);

    SaldoDTO getSaldo(String numeroCartao);

    Optional<TransactionErros> receiveTransaction(TransacaoDTO transacaoDTO);
}

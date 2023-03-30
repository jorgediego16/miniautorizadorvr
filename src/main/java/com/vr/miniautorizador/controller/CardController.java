package com.vr.miniautorizador.controller;

import com.vr.miniautorizador.domain.dto.CardDTO;
import com.vr.miniautorizador.domain.dto.SaldoDTO;
import com.vr.miniautorizador.domain.dto.TransacaoDTO;
import com.vr.miniautorizador.domain.enums.TransactionErros;
import com.vr.miniautorizador.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/cartoes")
    public ResponseEntity<CardDTO> save(@RequestBody CardDTO cardDTO) {
        CardDTO savedCard = cardService.save(cardDTO);
        return savedCard == null
                ? ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(cardDTO)
                : ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
    }

    @GetMapping(path = "/cartoes/{numeroCartao}")
    public ResponseEntity<SaldoDTO> getSaldo(@PathVariable String numeroCartao) {
        SaldoDTO saldoDTO = cardService.getSaldo(numeroCartao);
        return saldoDTO == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.status(HttpStatus.OK).body(saldoDTO);

    }

    @PostMapping("/transacoes")
    public ResponseEntity<String> receiveTransaction(@RequestBody TransacaoDTO transacaoDTO) {
        Optional<TransactionErros> transactionErros = this.cardService.receiveTransaction(transacaoDTO);
        return transactionErros.map(erros ->
                ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(erros.toString()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CREATED).body("OK"));
    }

}

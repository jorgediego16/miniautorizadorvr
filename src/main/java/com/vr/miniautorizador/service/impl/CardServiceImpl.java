package com.vr.miniautorizador.service.impl;

import com.vr.miniautorizador.domain.Card;
import com.vr.miniautorizador.domain.dto.CardDTO;
import com.vr.miniautorizador.domain.dto.SaldoDTO;
import com.vr.miniautorizador.domain.dto.TransacaoDTO;
import com.vr.miniautorizador.domain.enums.TransactionErros;
import com.vr.miniautorizador.domain.mapper.CardMapper;
import com.vr.miniautorizador.repository.CardRepository;
import com.vr.miniautorizador.service.CardService;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final CardMapper mapper;

    public CardServiceImpl(CardRepository cardRepository, CardMapper mapper) {
        this.cardRepository = cardRepository;
        this.mapper = mapper;
    }

    /**
     * Método responsável por salvar um cartão
     * @param cardDTO dto recebido na requisição
     * @return retorna o cartão salvo
     */
    public CardDTO save(CardDTO cardDTO) {
        cardDTO.setSaldo(500.0);
        return cardRepository.findByNumeroCartao(cardDTO.getNumeroCartao()).isPresent() ? null
                : mapper.toDTO(cardRepository.save(mapper.toDocument(cardDTO))) ;
    }

    /**
     * Método responsável por retornar o saldo de um cartão
     * @param numeroCartao número do cartão que será buscado o saldo
     * @return saldo do cartão
     */
    @Override
    public SaldoDTO getSaldo(String numeroCartao) {
        Optional<Card> card = cardRepository.findByNumeroCartao(numeroCartao);
        return card.map(value -> new SaldoDTO(value.getSaldo())).orElse(null);
    }

    /**
     * Método responsável por receber uma transação e debitar o valor da transação do saldo do cartão
     *
     * @param transacaoDTO dto contendo os dados da transação
     * @return retorna possíveis erros, ou um optional vazio caso tudo ocorra sem erros
     */
    @Override
    public Optional<TransactionErros> receiveTransaction(TransacaoDTO transacaoDTO) {
        Optional<Card> optionalCard = cardRepository.findByNumeroCartao(transacaoDTO.getNumeroCartao());
        if (optionalCard.isEmpty()) {
            return Optional.of(TransactionErros.CARTAO_INEXISTENTE);
        }
        Card card = optionalCard.get();
        if (!card.getSenha().equals(transacaoDTO.getSenhaCartao())) {
            return Optional.of(TransactionErros.SENHA_INVALIDA);
        }
        if (card.getSaldo() < transacaoDTO.getValor()) {
            return Optional.of(TransactionErros.SALDO_INSUFICIENTE);
        }
        double newSaldo = card.getSaldo() - transacaoDTO.getValor();
        card.setSaldo(newSaldo);

        try {
            cardRepository.save(card);
        } catch (OptimisticLockingFailureException e) {
            // Caso ocorra um erro de concorrência, retorna uma exceção
            return Optional.of(TransactionErros.CONCORRENCIA);
        }

        if (newSaldo < 0) {
            return Optional.of(TransactionErros.SALDO_INSUFICIENTE);
        }
        return Optional.empty();
    }



}

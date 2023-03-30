package com.vr.miniautorizador.domain.mapper.impl;

import com.vr.miniautorizador.domain.Card;
import com.vr.miniautorizador.domain.dto.CardDTO;
import com.vr.miniautorizador.domain.mapper.CardMapper;

public class CardMapperImpl implements CardMapper {
    @Override
    public Card toDocument(CardDTO cardDTO) {
        return cardDTO == null ? null : new Card(cardDTO.getNumeroCartao(), cardDTO.getSenha(), cardDTO.getSaldo());
    }

    @Override
    public CardDTO toDTO(Card card) {
        return card == null ? null : new CardDTO(card.getNumeroCartao(), card.getSenha(), card.getSaldo());
    }
}

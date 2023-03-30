package com.vr.miniautorizador.domain.mapper;

import com.vr.miniautorizador.domain.Card;
import com.vr.miniautorizador.domain.dto.CardDTO;
import org.mapstruct.Mapper;

/**
 * Classe responsável por mapear de um documento para um DTP, e vice-e-versa
 */
@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toDocument(CardDTO cardDTO);

    CardDTO toDTO(Card card);
}

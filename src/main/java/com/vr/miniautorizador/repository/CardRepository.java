package com.vr.miniautorizador.repository;

import com.vr.miniautorizador.domain.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interface responsavel pelo acesso ao banco de dados
 */
@Repository
public interface CardRepository extends MongoRepository<Card, String> {
    Optional<Card> findByNumeroCartao(String numeroCartao);
}

package com.vr.miniautorizador;

import com.vr.miniautorizador.domain.Card;
import com.vr.miniautorizador.domain.dto.CardDTO;
import com.vr.miniautorizador.domain.dto.SaldoDTO;
import com.vr.miniautorizador.domain.dto.TransacaoDTO;
import com.vr.miniautorizador.domain.enums.TransactionErros;
import com.vr.miniautorizador.domain.mapper.CardMapper;
import com.vr.miniautorizador.repository.CardRepository;
import com.vr.miniautorizador.service.impl.CardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class MiniAutorizadorApplicationTests {

	@Mock
	private CardRepository cardRepository;

	@Mock
	private CardMapper cardMapper;

	@InjectMocks
	private CardServiceImpl cardService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testSaveSuccess() {
		CardDTO cardDTO = new CardDTO("1234567890", "1234", 500.0);
		Card card = new Card("1234567890", "1234", 500.0);

		when(cardMapper.toDocument(cardDTO)).thenReturn(card);
		when(cardMapper.toDTO(card)).thenReturn(cardDTO);
		when(cardRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.empty());
		when(cardRepository.save(eq(card))).thenReturn(card);

		CardDTO savedCardDTO = cardService.save(cardDTO);

		assertNotNull(savedCardDTO);
		assertEquals(cardDTO.getNumeroCartao(), savedCardDTO.getNumeroCartao());
		assertEquals(cardDTO.getSenha(), savedCardDTO.getSenha());
		assertEquals(500.0, savedCardDTO.getSaldo(), 0.001);
		verify(cardRepository, times(1)).findByNumeroCartao(eq("1234567890"));
		verify(cardRepository, times(1)).save(eq(card));
	}

	@Test
	public void testGetSaldoSuccess() {
		Card card = new Card("1234567890", "1234", 500.0);
		when(cardRepository.save(eq(card))).thenReturn(card);
		when(cardRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.of(card));

		cardRepository.save(card);

		SaldoDTO saldoDTO = cardService.getSaldo("1234567890");
		assertNotNull(saldoDTO);
		assertEquals(500.0, saldoDTO.getSaldo(), 0.001);
	}

	@Test
	public void testGetSaldoNonExistingCard() {
		// Verifica se o método retorna null para um cartão inexistente
		when(cardRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.empty());
		SaldoDTO saldoDTO = cardService.getSaldo("12345678901");
		assertNull(saldoDTO);
	}

	@Test
	public void testGetSaldoNullCardNumber() {
		// Verifica se o método retorna null para um número de cartão nulo
		when(cardRepository.findByNumeroCartao(any(String.class))).thenReturn(Optional.empty());
		SaldoDTO saldoDTO = cardService.getSaldo(null);
		assertNull(saldoDTO);
	}

	@Test
	void testReceiveTransactionCardNotFound() {
		TransacaoDTO transacaoDTO = new TransacaoDTO("1234567890", "1234", 100.0);
		when(cardRepository.findByNumeroCartao(eq("1234567890"))).thenReturn(Optional.empty());

		Optional<TransactionErros> result = cardService.receiveTransaction(transacaoDTO);

		assertEquals(TransactionErros.CARTAO_INEXISTENTE, result.get());
		verify(cardRepository, times(1)).findByNumeroCartao(eq("1234567890"));
		verify(cardRepository, never()).save(any());
	}

	@Test
	void testReceiveTransactionInvalidPassword() {
		TransacaoDTO transacaoDTO = new TransacaoDTO("1234567890", "4321", 100.0);
		Card card = new Card("1234567890", "1234", 500.0);
		when(cardRepository.findByNumeroCartao(eq("1234567890"))).thenReturn(Optional.of(card));

		Optional<TransactionErros> result = cardService.receiveTransaction(transacaoDTO);

		assertEquals(TransactionErros.SENHA_INVALIDA, result.get());
		verify(cardRepository, times(1)).findByNumeroCartao(eq("1234567890"));
		verify(cardRepository, never()).save(any());
	}

	@Test
	void testReceiveTransactionInsufficientBalance() {
		TransacaoDTO transacaoDTO = new TransacaoDTO("1234567890", "1234", 1000.0);
		Card card = new Card("1234567890", "1234", 500.0);
		when(cardRepository.findByNumeroCartao(eq("1234567890"))).thenReturn(Optional.of(card));

		Optional<TransactionErros> result = cardService.receiveTransaction(transacaoDTO);

		assertEquals(TransactionErros.SALDO_INSUFICIENTE, result.get());
		verify(cardRepository, times(1)).findByNumeroCartao(eq("1234567890"));
		verify(cardRepository, never()).save(any());
	}

	@Test
	void testReceiveTransactionSuccess() {
		TransacaoDTO transacaoDTO = new TransacaoDTO("1234567890", "1234", 100.0);
		Card card = new Card("1234567890", "1234", 500.0);
		when(cardRepository.findByNumeroCartao(eq("1234567890"))).thenReturn(Optional.of(card));
		when(cardRepository.save(eq(card))).thenReturn(card);

		Optional<TransactionErros> result = cardService.receiveTransaction(transacaoDTO);

		assertFalse(result.isPresent());
		assertEquals(400.0, card.getSaldo(), 0.001);
		verify(cardRepository, times(1)).findByNumeroCartao(eq("1234567890"));
		verify(cardRepository, times(1)).save(eq(card));
	}


}

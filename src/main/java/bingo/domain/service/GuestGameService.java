package bingo.domain.service;

import bingo.domain.data.dto.CardDto;

public interface GuestGameService {

	public CardDto initCard(String gameName, String guestName);

	public CardDto refreshCard(String gameName, String guestName);

	public boolean checkCard(CardDto cardDto);
}
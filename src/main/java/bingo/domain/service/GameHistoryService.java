package bingo.domain.service;

import bingo.app.rest.data.response.GameHistoryResponse;

public interface GameHistoryService {

	public GameHistoryResponse getGameHistory(String gameName);
}

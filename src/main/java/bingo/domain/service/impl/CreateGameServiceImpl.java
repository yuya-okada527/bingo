package bingo.domain.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bingo.app.form.CreateGameForm;
import bingo.domain.exception.GameAlreadyExistsException;
import bingo.domain.service.CreateGameService;
import bingo.infrastructure.mapper.BingoGameMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreateGameServiceImpl implements CreateGameService {

	private final BingoGameMapper bingoGameMapper;

	@Transactional(readOnly = false)
	public void createGame(CreateGameForm createGameForm) {
		try {
			this.bingoGameMapper.insertGame(createGameForm.getGameName());
		} catch (Throwable e) {
			throw new GameAlreadyExistsException(createGameForm.getGameName(), e);
		}
	}
}

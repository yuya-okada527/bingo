package bingo.domain.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bingo.app.form.GuestLoginForm;
import bingo.domain.exception.NameAlreadyUsedException;
import bingo.domain.exception.NoSuchGameException;
import bingo.domain.service.GuestLoginService;
import bingo.infrastructure.mapper.BingoGameMapper;
import bingo.infrastructure.mapper.GuestMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GuestLoginServiceImpl implements GuestLoginService {

	private final GuestMapper guestMapper;

	private final BingoGameMapper bingoGameMapper;

	@Transactional(readOnly = false)
	public void join(GuestLoginForm guestLoginForm) {

		try {
			this.guestMapper.insertGuest(guestLoginForm.getGuestName(),
										 guestLoginForm.getGameName());
		} catch (DuplicateKeyException e) {
			throw new NameAlreadyUsedException("name already used", e);
		} catch (DataIntegrityViolationException e) {
			throw new NoSuchGameException("no such game", e);
		}
	}

	public List<String> init() {

		// 開催中のビンゴゲームを全て取得
		return this.bingoGameMapper.selectAllGameNames();
	}
}

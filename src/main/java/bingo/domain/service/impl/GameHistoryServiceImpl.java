package bingo.domain.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bingo.app.rest.data.response.GameHistoryResponse;
import bingo.domain.data.model.BingoGameModel;
import bingo.domain.service.GameHistoryService;
import bingo.infrastructure.mapper.BingoGameMapper;
import bingo.util.bingo.BingoUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameHistoryServiceImpl implements GameHistoryService {

	private final BingoGameMapper bingoGameMapper;

	private final BingoUtil bingoUtil;

	@Override
	public GameHistoryResponse getGameHistory(String gameName) {

		// ビンゴゲームを取得
		BingoGameModel bingoGameModel = this.bingoGameMapper.selectGame(gameName);

		// ビンゴゲームの履歴に変換
		List<Integer> bingoHistory =
				this.bingoUtil.convertFromNumArrayStr(bingoGameModel.getNumArrayString());
		// 0を削除
		bingoHistory.remove(0);

		return GameHistoryResponse.builder()
								  .gameHistory(bingoHistory).build();
	}
}

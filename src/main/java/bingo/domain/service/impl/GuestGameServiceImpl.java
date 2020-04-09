package bingo.domain.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bingo.domain.data.dto.CardDto;
import bingo.domain.data.model.BingoGameModel;
import bingo.domain.data.model.CardModel;
import bingo.domain.exception.NoSuchGameException;
import bingo.domain.service.GuestGameService;
import bingo.infrastructure.mapper.BingoGameMapper;
import bingo.infrastructure.mapper.CardMapper;
import bingo.util.bingo.BingoUtil;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GuestGameServiceImpl implements GuestGameService {

	private final CardMapper cardMapper;

	private final BingoGameMapper bingoGameMapper;

	private final BingoUtil bingoUtil;

	@Transactional(readOnly = false)
	public CardDto initCard(String gameName, String guestName) {

		// 1~75までの整数のリストを取得する
		List<Integer> allNumList =
				IntStream.rangeClosed(1, 75).boxed().collect(Collectors.toList());

		// シャッフルする
		Collections.shuffle(allNumList);

		// 前25個を取得する
		List<Integer> cardNumList = allNumList.subList(0, 25);
		Collections.sort(cardNumList);
		cardNumList.set(12, 0);

		// TODO 動作確認
		if (cardNumList.size() != 25) {
			throw new RuntimeException("数違うよなんかおかしい");
		}

		// Cardを登録する
		this.cardMapper.insertCard(gameName + guestName,
				cardNumList.stream()
						   .map(num -> String.valueOf(num))
					       .collect(Collectors.joining(",")));


		// Gameの状態を取得する
		BingoGameModel bingoGameModel = this.bingoGameMapper.selectGame(gameName);
		if (bingoGameModel == null) {
			throw new NoSuchGameException("そんなゲームありゃせん");
		}
		List<Integer> bingoNumList =
				this.bingoUtil.convertFromNumArrayStr(bingoGameModel.getNumArrayString());

		int[][] squaresNum = this.map2IntegerArray(cardNumList);
		boolean[][] squaresBoolean =
				this.map2BoolArray(this.checkCard(cardNumList, bingoNumList));

		return CardDto.builder()
				      .squaresNum(squaresNum)
				      .squaresBoolean(squaresBoolean).build();
	}

	public CardDto refreshCard(String gameName, String guestName) {

		// Cardを取得する
		CardModel cardModel = this.cardMapper.selectCard(gameName + guestName);
		List<Integer> cardNumList =
				this.bingoUtil.convertFromNumArrayStr(cardModel.getCardNumArrayString());

		// Bingoの状態を取得する
		BingoGameModel bingoGameModel = this.bingoGameMapper.selectGame(gameName);
		List<Integer> bingoNumList =
				this.bingoUtil.convertFromNumArrayStr(bingoGameModel.getNumArrayString());

		int[][] squaresNum = this.map2IntegerArray(cardNumList);
		boolean[][] squaresBoolean =
				this.map2BoolArray(this.checkCard(cardNumList, bingoNumList));

		return CardDto.builder()
					  .squaresNum(squaresNum)
					  .squaresBoolean(squaresBoolean).build();
	}

	public boolean checkCard(CardDto cardDto) {

		if (this.checkHolizontal(cardDto)) {
			return true;
		}

		if (this.checkVertical(cardDto)) {
			return true;
		}

		if (this.checkDiagonal(cardDto)) {
			return true;
		}

		return false;
	}

	private boolean checkHolizontal(CardDto cardDto) {
		boolean[][] squaresBool = cardDto.getSquaresBoolean();
		for (int i = 0; i < 5; i++) {
			int count = 0;
			for (int j = 0; j < 5; j++) {
				if (squaresBool[j][i]) {
					count++;
				}
			}
			if (count == 5) {
				return true;
			}
		}
		return false;
	}

	private boolean checkVertical(CardDto cardDto) {
		boolean[][] squaresBool = cardDto.getSquaresBoolean();
		for (int i = 0; i < 5; i++) {
			int count = 0;
			for (int j = 0; j < 5; j++) {
				if (squaresBool[i][j]) {
					count++;
				}
			}
			if (count == 5) {
				return true;
			}
		}
		return false;
	}

	private boolean checkDiagonal(CardDto cardDto) {
		boolean[][] squaresBool = cardDto.getSquaresBoolean();
		int count = 0;
		for (int i = 0; i < 5; i++) {
			if (squaresBool[i][i]) {
				count++;
			}
		}

		if (count == 5) {
			return true;
		}

		count = 0;
		for (int i = 0; i< 5; i++) {
			if (squaresBool[4-i][i]) {
				count++;
			}
		}

		if (count == 5) {
			return true;
		}

		return false;
	}

	private List<Boolean> checkCard(List<Integer> cardNumList,
									List<Integer> bingoNumList) {
		List<Boolean> boolList = new ArrayList<>();
		for (int i = 0; i < cardNumList.size(); i++) {
			if (bingoNumList.contains(cardNumList.get(i))) {
				boolList.add(true);
			} else {
				boolList.add(false);
			}
		}
		return boolList;

	}

	private int[][] map2IntegerArray(List<Integer> numList) {
		// 数値リストを5*5の二次元配列に変換する
		int[][] squaresNum = new int[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				squaresNum[i][j] = numList.get(j + (i * 5));
			}
		}

		return squaresNum;
	}

	private boolean[][] map2BoolArray(List<Boolean> boolList) {
		// 真偽値のリストを5*5の二次元配列に変換する
		boolean[][] squaresBoolean = new boolean[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				squaresBoolean[i][j] = boolList.get(j + (i * 5));
			}
		}

		return squaresBoolean;
	}
}

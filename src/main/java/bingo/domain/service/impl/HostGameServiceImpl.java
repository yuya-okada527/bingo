package bingo.domain.service.impl;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bingo.domain.data.model.BingoGameModel;
import bingo.domain.service.HostGameService;
import bingo.infrastructure.mapper.BingoGameMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HostGameServiceImpl implements HostGameService {

	private final BingoGameMapper bingoGameMapper;

	private static final List<Integer> INITIAL_LIST =
			IntStream.rangeClosed(0, 75).boxed().collect(Collectors.toList());

	@Transactional(readOnly = false)
	public void start(String gameName) {

		// 登録済みのビンゴゲームを取得する
		BingoGameModel bingoGameModel = this.bingoGameMapper.selectGame(gameName);

		// ビンゴゲームを開始する
		bingoGameModel.setNumArrayString("0");
		this.bingoGameMapper.updateGame(bingoGameModel.getGameName(), bingoGameModel.getNumArrayString());
	}

	@Transactional(readOnly = false)
	public int next(String gameName) {

		// 既に引かれたくじのリストを取得する
		BingoGameModel bingoGameModel = this.bingoGameMapper.selectGame(gameName);
		List<Integer> pastNums = Stream.of(bingoGameModel.getNumArrayString().split(","))
				                       .map(num -> Integer.valueOf(num))
				                       .collect(Collectors.toList()) ;

		// まだ引かれていないくじのリストを取得する
		List<Integer> possibleNums = ListUtils.subtract(INITIAL_LIST, pastNums)
				                             .stream()
				                             .collect(Collectors.toList());

		// 次のくじを取得する
		Random random = new Random();
		Integer nextNum = possibleNums.get(random.nextInt(possibleNums.size()));

		// 取得したくじをDBに保存する
		pastNums.add(nextNum);
		bingoGameModel.setNumArrayString(pastNums.stream()
												 .map(num -> String.valueOf(num))
												 .collect(Collectors.joining(",")));
		this.bingoGameMapper.updateGame(bingoGameModel.getGameName(), bingoGameModel.getNumArrayString());

		return nextNum;
	}
}

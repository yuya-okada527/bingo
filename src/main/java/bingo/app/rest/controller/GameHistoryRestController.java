package bingo.app.rest.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bingo.app.rest.data.request.GameHistoryRequest;
import bingo.app.rest.data.response.GameHistoryResponse;
import bingo.domain.service.GameHistoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/guest/game")
@RequiredArgsConstructor
public class GameHistoryRestController {

	private final GameHistoryService gameHistoryService;

	@GetMapping("/history")
	public GameHistoryResponse getGameHistory(@RequestParam String gameName) {

		// TODO 動作確認
		System.out.println("gameName: " + gameName);

		return this.gameHistoryService.getGameHistory(gameName);
	}

	@GetMapping("/hoge")
	public GameHistoryRequest getHoge(@RequestParam @Valid GameHistoryRequest request, BindingResult result) {

		result.getAllErrors().stream().forEach(System.out::println);

		return request;
	}
}

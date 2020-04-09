package bingo.app.controller;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import bingo.domain.data.dto.CardDto;
import bingo.domain.service.GuestGameService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/guest")
@RequiredArgsConstructor
public class GuestGameController {

	private static final String GUEST_GAME = "guest_game";

	private final GuestGameService guestGameService;

	@GetMapping("/game/{gameName}/{guestName}")
	public String init(@PathVariable String gameName,
					   @PathVariable String guestName,
					   Model model) {

		// TODO 動作確認
		System.out.println("gameName: " + gameName);
		System.out.println("guestName: " + guestName);

		// ビンゴカードを取得する
		CardDto cardDto = null;
		try {
			cardDto = this.guestGameService.initCard(gameName, guestName);
		} catch (DuplicateKeyException e) {
			cardDto = this.guestGameService.refreshCard(gameName, guestName);
		}


		// modelにセットして遷移
		model.addAttribute("card", cardDto);
		model.addAttribute("gameName", gameName);
		model.addAttribute("guestName", guestName);
		return GUEST_GAME;
	}

	@GetMapping("/game/{gameName}/{guestName}/refresh")
	public String refresh(@PathVariable String gameName,
						  @PathVariable String guestName,
						  Model model) {

		// TODO 動作確認
		System.out.println("gameName: " + gameName);
		System.out.println("guestName: " + guestName);

		// ビンゴカードの状態を最新化する
		CardDto cardDto = this.guestGameService.refreshCard(gameName, guestName);

		// ビンゴしてるかチェックする
		boolean bingo = this.guestGameService.checkCard(cardDto);

		// modelにセットして遷移
		model.addAttribute("card", cardDto);
		model.addAttribute("bingo", bingo);
		model.addAttribute("gameName", gameName);
		model.addAttribute("guestName", guestName);
		return GUEST_GAME;
	}
}

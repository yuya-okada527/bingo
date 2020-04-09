package bingo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import bingo.app.form.HostGameForm;
import bingo.domain.service.HostGameService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/host")
@RequiredArgsConstructor
public class HostGameController {

	private static final String HOST_GAME = "host_game";

	private final HostGameService hostGameService;

	@GetMapping("/game/{gameName}")
	public String init(@PathVariable String gameName,
					   Model model) {
		// TODO 動作確認
		System.out.println("gameName: " + gameName);

		// ゲーム名をセットして遷移
		model.addAttribute("hostGameForm",
				HostGameForm.builder()
				            .gameName(gameName)
				            .nextAction("Start").build());
		return HOST_GAME;
	}

	@GetMapping("/game/{gameName}/Start")
	public String start(@PathVariable String gameName,
						Model model) {
		// TODO 動作確認
		System.out.println("gameName: " + gameName);

		// ゲームの開始
		this.hostGameService.start(gameName);

		// TODO 動作確認
		System.out.println("Game Started");

		// ゲームを開始状態にして遷移
		model.addAttribute("hostGameForm",
				HostGameForm.builder()
							.gameName(gameName)
							.nextAction("Next").build());
		return HOST_GAME;
	}

	@GetMapping("/game/{gameName}/Next")
	public String NextAction(@PathVariable String gameName,
							 Model model) {
		// TODO 動作確認
		System.out.println("gameName: " + gameName);

		// ゲームのステップを進める
		Integer nextNum = null;
		try {
			nextNum = this.hostGameService.next(gameName);
		} catch (RuntimeException e) {
			// 全てのくじを引いた場合
			e.printStackTrace();
			nextNum = 76;
		}


		// TODO 動作確認
		System.out.println("nextNum: " + nextNum);

		// ゲームの状態をセットして遷移
		model.addAttribute("hostGameForm",
				HostGameForm.builder()
							.gameName(gameName)
							.nextNum(nextNum)
							.nextAction("Next").build());

		return HOST_GAME;
	}

	@ModelAttribute
	HostGameForm setupForm() {
		return new HostGameForm();
	}
}

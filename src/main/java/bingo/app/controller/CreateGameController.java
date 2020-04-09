package bingo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bingo.app.form.CreateGameForm;
import bingo.domain.exception.GameAlreadyExistsException;
import bingo.domain.service.CreateGameService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/host")
@RequiredArgsConstructor
public class CreateGameController {

	private static final String CREATE_GAME = "create_game";

	private final CreateGameService createGameService;

	@GetMapping("/create")
	public String init() {
		return CREATE_GAME;
	}

	@PostMapping("/create")
	public String createGame(@Validated CreateGameForm createGameForm,
							 BindingResult result,
							 RedirectAttributes redirectAttributes) {
		// TODO 動作確認
		System.out.println("createGameForm: " + createGameForm);

		// Bean Validation
		if (result.hasErrors()) {
			// TODO 動作確認
			System.out.println("result.getAllErrors(): " + result.getAllErrors());
		}

		try {
			this.createGameService.createGame(createGameForm);
		} catch (GameAlreadyExistsException e) {
			// TODO 動作確認
			e.printStackTrace();
		}

		// TODO 動作確認
		System.out.println("success");

		redirectAttributes.addAttribute("gameName", createGameForm.getGameName());
		return "redirect:/host/game/{gameName}";
	}

	@ModelAttribute
	CreateGameForm setupForm() {
		return new CreateGameForm();
	}
}

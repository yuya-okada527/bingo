package bingo.app.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bingo.app.form.GuestLoginForm;
import bingo.domain.exception.NameAlreadyUsedException;
import bingo.domain.exception.NoSuchGameException;
import bingo.domain.service.GuestLoginService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/guest")
@RequiredArgsConstructor
public class GuestLoginController {

	private static final String GUEST_LOGIN = "guest_login";

	private final GuestLoginService guestLoginService;

	private final MessageSource msg;

	@GetMapping(path = "/login")
	public String init(Model model) {

		// 開催中のゲームを取得
		List<String> games = this.guestLoginService.init();

		// 開催中のゲームをセット
		model.addAttribute("games", games);

		return GUEST_LOGIN;
	}

	@PostMapping(path = "/login")
	public String join(@Validated GuestLoginForm guestLoginForm,
					   BindingResult result,
					   RedirectAttributes redirectAttributes,
					   Model model) {
		// TODO動作確認
		System.out.println("guestLoginForm: " + guestLoginForm);

		// PK違反用(暫定対応)
		// 開催中のゲームを取得
		List<String> games = this.guestLoginService.init();

		// 開催中のゲームをセット
		model.addAttribute("games", games);

		// Bean Validation
		if (result.hasErrors()) {
			// TODO 動作確認
			System.out.println("result.getAllErrors(): " + result.getAllErrors());
			return GUEST_LOGIN;
		}

		// Guestの追加
		try {
			this.guestLoginService.join(guestLoginForm);
		} catch (NoSuchGameException e) {
			// TODO 動作確認
			e.printStackTrace();

			model.addAttribute("gameNameNotExists", true);
			model.addAttribute("gameErrorMessage",
					msg.getMessage("game.error.001",
							new String[] {guestLoginForm.getGameName()},
							Locale.JAPAN));
			return GUEST_LOGIN;
		} catch (NameAlreadyUsedException e) {
			// TODO 動作確認
			e.printStackTrace();

			model.addAttribute("sameUserNameExists", true);
			model.addAttribute("userErrorMessage",
					this.msg.getMessage("user.error.001",
							new String[] {guestLoginForm.getGuestName()},
							Locale.JAPAN));
			return GUEST_LOGIN;
		}

		// Redirect先との連携
		redirectAttributes.addAttribute("gameName", guestLoginForm.getGameName());
		redirectAttributes.addAttribute("guestName", guestLoginForm.getGuestName());
		return "redirect:/guest/game/{gameName}/{guestName}";
	}

	@ModelAttribute
	GuestLoginForm setupForm() {
		return new GuestLoginForm();
	}

}

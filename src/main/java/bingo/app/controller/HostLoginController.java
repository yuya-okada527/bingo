package bingo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bingo.app.form.HostLoginForm;
import bingo.domain.exception.NoDataException;
import bingo.domain.service.HostLoginService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/host")
@RequiredArgsConstructor
public class HostLoginController {

	private static final String HOST_LOGIN = "host_login";

	private final HostLoginService hostLoginService;

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String init() {
		return HOST_LOGIN;
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String login(@Validated HostLoginForm hostLoginForm,
						BindingResult result) {
		// Bean Validation
		if (result.hasErrors()) {
			// TODO 動作確認
			System.out.println(hostLoginForm);
			System.out.println(result.getAllErrors());
			return HOST_LOGIN;
		}

		System.out.println(hostLoginForm);

		try {
			this.hostLoginService.login(hostLoginForm);
		} catch (NoDataException e) {
			e.printStackTrace();
			return HOST_LOGIN;
		}

		System.out.println("success");

		return "redirect:/host/create";
	}

	@ModelAttribute
	HostLoginForm setupForm() {
		return new HostLoginForm();
	}
}

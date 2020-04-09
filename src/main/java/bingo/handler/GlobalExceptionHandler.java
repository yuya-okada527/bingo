package bingo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import bingo.domain.service.MailSendService;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private final MailSendService mailSendService;

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handlerException(Throwable e) {

		// スタックトレースを出しとく
		e.printStackTrace();

		// メールを送信する
		this.mailSendService.sendErrorMail(e);

		return "error/500";
	}
}

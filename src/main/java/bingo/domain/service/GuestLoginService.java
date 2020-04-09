package bingo.domain.service;

import java.util.List;

import bingo.app.form.GuestLoginForm;

public interface GuestLoginService {

	public void join(GuestLoginForm guestLoginForm);

	public List<String> init();
}

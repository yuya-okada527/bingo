package bingo.domain.service;

import bingo.app.form.HostLoginForm;
import bingo.domain.data.model.HostModel;

public interface HostLoginService {

	public HostModel login(HostLoginForm hostLoginForm);

}

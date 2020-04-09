package bingo.domain.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bingo.app.form.HostLoginForm;
import bingo.domain.data.model.HostModel;
import bingo.domain.exception.NoDataException;
import bingo.domain.service.HostLoginService;
import bingo.infrastructure.mapper.HostMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HostLoginServiceImpl implements HostLoginService {

	private final HostMapper hostMapper;

	public HostModel login(HostLoginForm hostLoginForm) {

		// ホストの取得
		HostModel hostModel = this.hostMapper.selectHost(hostLoginForm.getName(), hostLoginForm.getPassword());

		// ホストが取得できなかった場合
		if (hostModel == null) {
			throw new NoDataException("Name or Password is wrong");
		}

		return hostModel;
	}
}

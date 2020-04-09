package bingo.domain.service;

public interface HostGameService {

	public void start(String gameName);

	public int next(String gameName);
}

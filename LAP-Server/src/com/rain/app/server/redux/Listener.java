package com.rain.app.server.redux;

public interface Listener {
	public abstract <K> void invoke(K key);
}

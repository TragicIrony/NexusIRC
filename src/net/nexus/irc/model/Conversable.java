package net.nexus.irc.model;

import java.util.Set;

/**
 * @author Kevin
 */
public interface Conversable {

	Set<Mode> getModes();

	String getName();

	void message(String message);

	void notice(String notice);

}

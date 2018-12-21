package net.nexus.irc.event;

/**
 * @author Kevin
 */
public interface IRCEventListener {

	default void onEvent(IRCEvent event) {}

}

package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.ErrorEvent;

/**
 * @author Kevin
 */
public interface ErrorListener extends IRCEventListener {

	void onError(ErrorEvent event);

}

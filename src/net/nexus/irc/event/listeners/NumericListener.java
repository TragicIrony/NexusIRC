package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.NumericEvent;

/**
 * @author Kevin
 */
public interface NumericListener extends IRCEventListener {

	void onNumeric(NumericEvent event);

}

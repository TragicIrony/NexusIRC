package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.ModeEvent;

/**
 * @author Kevin
 */
public interface ModeListener extends IRCEventListener {

	void onMode(ModeEvent event);

}

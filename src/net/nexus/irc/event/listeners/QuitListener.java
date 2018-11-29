package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.QuitEvent;

/**
 * @author Kevin
 */
public interface QuitListener extends IRCEventListener {

	void onQuit(QuitEvent event);

}

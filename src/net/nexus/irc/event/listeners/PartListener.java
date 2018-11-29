package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.PartEvent;

/**
 * @author Kevin
 */
public interface PartListener extends IRCEventListener {

	void onPart(PartEvent event);

}

package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.NoticeEvent;

/**
 * @author Kevin
 */
public interface NoticeListener extends IRCEventListener {

	void onNotice(NoticeEvent event);

}

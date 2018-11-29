package net.nexus.irc.event.listeners;

import net.nexus.irc.event.IRCEventListener;
import net.nexus.irc.event.events.TopicEvent;

/**
 * @author Kevin
 */
public interface TopicListener extends IRCEventListener {

	void onTopic(TopicEvent event);

}

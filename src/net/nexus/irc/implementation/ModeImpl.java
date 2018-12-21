package net.nexus.irc.implementation;

import net.nexus.irc.Client;
import net.nexus.irc.model.Mode;
import net.nexus.irc.util.Sanity;

import java.util.Objects;

/**
 * @author Kevin
 */
final class ModeImpl implements Mode {

	private final Client client;

	private final char flag;
	private final String parameter;

	ModeImpl(Client client, char flag, String parameter) {
		this.client = Sanity.nonNull(client, "ModeImpl#(null, char, String");
		this.flag = flag;
		this.parameter = parameter == null ? "" : parameter;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Mode) {
			ModeImpl mode = (ModeImpl) object;
			return client.equals(mode.client) && flag == mode.flag && parameter.equalsIgnoreCase(mode.parameter);
		}
		return  false;
	}

	@Override
	public char getFlag() {
		return flag;
	}

	@Override
	public String getParameter() {
		return parameter;
	}

	@Override
	public int hashCode() {
		return Objects.hash(flag, parameter, client);
	}

	@Override
	public String toString() {
		return "Mode[flag=" + flag + ", parameter=" + parameter + "]";
	}

}

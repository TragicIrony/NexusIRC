package net.nexus.irc.util;

/**
 * @author Kevin
 */
public final class Sanity {

	private Sanity() {}

	public static String nonEmpty(String string, String error) {
		if (net.kevin.nexus.irc.util.Sanity.nonNull(string).isEmpty())
			throw new net.kevin.nexus.irc.util.Sanity.EmptyStringException(error);

		return string;
	}

	public static <O> O nonNull(O o) {
		return nonNull(o, "object cannot be null");
	}

	public static <O> O nonNull(O o, String error) {
		if (o == null)
			throw new NullPointerException(error);

		return o;
	}

	public static class EmptyStringException extends RuntimeException {

		public EmptyStringException(String error) {
			super(error);
		}

	}

}

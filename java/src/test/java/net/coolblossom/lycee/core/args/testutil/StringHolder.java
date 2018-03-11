package net.coolblossom.lycee.core.args.testutil;

import javax.annotation.Nonnull;

public class StringHolder {
	@Nonnull String value;
	public StringHolder(@Nonnull final String arg) {
		value = arg;
	}

	@Override
	public String toString() {
		return value;
	}
}
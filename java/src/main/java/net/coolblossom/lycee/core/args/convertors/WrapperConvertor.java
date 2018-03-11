package net.coolblossom.lycee.core.args.convertors;

import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.log4j.Logger;

public class WrapperConvertor extends Convertor {
	private static final Logger logger = Logger.getLogger(WrapperConvertor.class);

	@Nonnull
	private final Function<String, Object> parser;

	protected WrapperConvertor(@Nonnull final Class<?> typeClass,
			@Nonnull final Function<String, Object> parser) {
		super(typeClass);
		this.parser = parser;
		logger.info("wrapper class:" + typeClass.getName());
	}

	@Nonnull
	@Override
	public Object convert(@Nonnull final String str) {
		logger.info("str:" + str);
		return parser.apply(str);
	}

}

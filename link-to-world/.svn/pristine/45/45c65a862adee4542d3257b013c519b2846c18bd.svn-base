package io.sited.db.impl.jdbc.dialect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a dialect of SQL implemented by a particular RDBMS. Subclasses
 * implement Hibernate compatibility with different systems.<br>
 * <br>
 * Subclasses should provide a public default constructor that
 * <tt>register()</tt> a set of type mappings and default Hibernate properties.
 * <br>
 * <br>
 * Subclasses should be immutable.
 *
 * @author Gavin King, David Channon
 */
public abstract class Dialect {

	private static final Logger log = LoggerFactory.getLogger(Dialect.class);

	public static final String DEFAULT_BATCH_SIZE = "15";
	public static final String NO_BATCH = "0";

	/**
	 * Characters used for quoting SQL identifiers
	 */
	public static final String QUOTE = "`\"[";
	public static final String CLOSED_QUOTE = "`\"]";

}

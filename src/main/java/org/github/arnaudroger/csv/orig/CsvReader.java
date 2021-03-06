package org.github.arnaudroger.csv.orig;

import org.github.arnaudroger.csv.CellConsumer;
import org.github.arnaudroger.csv.CheckedConsumer;
import org.github.arnaudroger.csv.StringArrayCellConsumer;

import java.io.IOException;




public final class CsvReader  {

	private final CharConsumer consumer;


	public CsvReader(CharConsumer charConsumer) {
		this.consumer = charConsumer;
	}

	/**
	 * parse cvs
     * @param cellConsumer the consumer that the parser will callback
	 * @param <CC> the cell consumer type
     * @throws IOException if an io error occurs
     * @return the cell consumer
	 */
	public <CC extends CellConsumer> CC parseAll(CC cellConsumer)
			throws IOException {
		_parseAll(cellConsumer);

		return cellConsumer;
	}


	private <CC extends CellConsumer> void _parseAll(CC cellConsumer) throws IOException {
		do {
			consumer.consumeAllBuffer(cellConsumer);
			consumer.shiftBufferToMark();
		} while (consumer.refillBuffer());
		consumer.finish(cellConsumer);
	}

	public <RH extends CheckedConsumer<String[]>> RH read(RH consumer) throws IOException {
		parseAll(toCellConsumer(consumer));
		return consumer;
	}

	private CellConsumer toCellConsumer(CheckedConsumer<String[]> consumer) {
		return StringArrayCellConsumer.newInstance(consumer);
	}
}

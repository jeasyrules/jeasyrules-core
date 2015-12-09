package org.jeasyrules.core.decisiontable.impl;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.jeasyrules.core.decisiontable.DecisionTable;
import org.jeasyrules.core.decisiontable.DecisionTableLoaderFromFile;
import org.jeasyrules.core.decisiontable.ValidationRule;

/**
 * CSV loading implementation of decision table.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 * @param <T>
 */
public abstract class AbstractCSVDecisionLoader<T extends Serializable> implements DecisionTableLoaderFromFile<T> {
	protected CSVParser parser;

	private DecisionTable<T> decisionTable;
	private List<String> headers;
	private List<ValidationRule<T>> validationRules;
	protected String validationOperator;

	/**
	 * Loading CSV into decision table with a specified separator.
	 * 
	 * @param in
	 * @param separator
	 * @throws IOException
	 */
	public void load(InputStream in, Character separator) throws IOException {
		Reader reader = new InputStreamReader(new BOMInputStream(in));
		parser = CSVFormat.newFormat(separator).withHeader().parse(reader);

		Map<String, Boolean> hashExists = new HashMap<String, Boolean>();
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		parser.forEach(r -> processRecord(r, rows, hashExists));

		decisionTable = new HashMapDecisionTableImpl<T>();
		decisionTable.setValidationRules(validationRules);
		((HashMapDecisionTableImpl<T>) decisionTable).setHeaders(this.headers);
		((HashMapDecisionTableImpl<T>) decisionTable).setRows(rows);
		((HashMapDecisionTableImpl<T>) decisionTable).setValidationOperator(validationOperator);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IOException
	 */
	@Override
	public void load(InputStream in) throws IOException {
		this.load(in, ';');
	}

	/**
	 * Processing record.
	 * 
	 * @param r
	 * @param decisions
	 * @throws Exception
	 */
	private void processRecord(CSVRecord r, List<Map<String, String>> rows, Map<String, Boolean> hashExists) {
		Map<String, String> record = r.toMap();
		StringJoiner hash = new StringJoiner(";");

		if (isEmpty(headers)) {
			Set<String> keyset = record.keySet();
			headers = new ArrayList<String>(keyset);
		}

		if (isNotEmpty(headers)) {
			Collections.sort(headers);
			for (String header : headers) {
				String value = record.get(header);
				if (null == value) {
					value = "";
				}

				value = value.trim();

				// If we find a header line which is not at the beginning of the
				// file
				if (isNotEmpty(value) && headers.contains(value)) {
					return;
				}

				hash.add(value);
			}

			// If the line is not already found in the file
			if (!hashExists.containsKey(hash.toString())) {
				hashExists.put(hash.toString(), true);
				rows.add(record);
			}
		}
	}

	/**
	 * @return the decisionTable
	 */
	@Override
	public DecisionTable<T> getDecisionTable() {
		return decisionTable;
	}

	/**
	 * @return the validationRules
	 */
	@Override
	public List<ValidationRule<T>> getValidationRules() {
		return validationRules;
	}

	/**
	 * @param validationRules
	 *            the validationRules to set
	 */
	@Override
	public void setValidationRules(List<ValidationRule<T>> validationRules) {
		this.validationRules = validationRules;
	}

	/**
	 * @return the validationOperator
	 */
	public String getValidationOperator() {
		return validationOperator;
	}

	/**
	 * @param validationOperator
	 *            the validationOperator to set
	 */
	public void setValidationOperator(String validationOperator) {
		this.validationOperator = validationOperator;
	}
}

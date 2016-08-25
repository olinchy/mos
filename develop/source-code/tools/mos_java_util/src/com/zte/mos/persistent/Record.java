package com.zte.mos.persistent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

import static com.zte.mos.util.basic.Logger.logger;

public class Record implements Serializable, Cloneable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String entityName = "";
	private String[] primaryKeys = new String[0];
	private HashMap<String, Object> valueMap = null;

	public Record(String tableName, String... primaryKeys) {
		this(tableName, new HashMap<String, Object>(), primaryKeys);
	}

	public Record(String tableName, HashMap<String, Object> valueMap, String... primaryKeys) {
		this.entityName = tableName;
		this.valueMap = valueMap;

		if (primaryKeys == null) {
			logger(Record.class).error("no primary keys when create Record for " + entityName,
					null);
		} else {
			this.primaryKeys = primaryKeys;
		}
	}

	private Record() {
	}

    public boolean isEmpty(){
        return this.valueMap.isEmpty();
    }

	//    public HashMap<String, Object> getValueMap()
//    {
//        return valueMap;
//    }
	public void put(String fieldName, Object value) {
		this.valueMap.put(fieldName.toUpperCase(), value);
	}

	/**
	 * @return the tableName
	 */
	public final String getTableName() {
		return entityName;
	}

	public void add(String columnName, Object value) {
		this.valueMap.put(columnName.toUpperCase(), value);
	}

	/**
	 * @param tableName the tableName to set
	 */
	public final void setTableName(String tableName) {
		this.entityName = tableName;
	}

	public final Object get(String fieldName) {
		return this.valueMap.get(fieldName.toUpperCase());
	}

	/**
	 * @return the keyColumns
	 */
	public final String[] primaryKeys() {
		return primaryKeys;
	}

	@Override
	public String toString() {
		return valueMap != null ? valueMap.toString() : "value map is null";
	}

	@Override
	public Record clone() {
		Record clone = new Record();
		clone.entityName = entityName;
		clone.primaryKeys = primaryKeys;
		for (Entry<String, Object> entry : valueMap.entrySet()) {
			clone.valueMap.put(entry.getKey(), entry.getValue());
		}
		return clone;

	}

	public Iterable<? extends Entry<String, Object>> all() {
		return this.valueMap.entrySet();
	}
}

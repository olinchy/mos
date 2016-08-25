package com.zte.mos.persistent;

import com.zte.mos.exception.PersistentException;

import java.util.List;

public interface PersistentContext
{
    Record createRecord(String entityName, String[] keyColumns);

    List<Record> readRecord(String entityName, String filter) throws PersistentException;

    boolean delete(Record record) throws PersistentException;

    boolean create(Record record) throws PersistentException;

    boolean update(Record record) throws PersistentException;

    PersistentContext spawn(String mosDN);

    void clear();
}

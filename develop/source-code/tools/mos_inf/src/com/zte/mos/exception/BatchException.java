package com.zte.mos.exception;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by luoqingkai on 15-10-20.
 */
public class BatchException extends Exception {
    private List<Exception> exceptionList = new LinkedList<Exception>();

    public BatchException() {

    }

    public final void addException(Exception e) {
        exceptionList.add(e);
    }

    public List<Exception> getExceptionList() {
        return exceptionList;
    }

    public boolean hasException() {
        return !exceptionList.isEmpty();
    }
}

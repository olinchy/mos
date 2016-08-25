package com.zte.mos.domain;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by luoqingkai on 2/23/16.
 */
public class TransactionLog {

    public static final TransactionLog nullTransactionLog = new TransactionLog();

    private  TransactionLog()
    {

    }

    public void merge(TransactionLog log)
    {
        queue.addAll(log.queue);
    }

    private Queue<OperationLog> queue = new LinkedList<OperationLog>();

    public TransactionLog(Queue<OperationLog> queue) {
        this.queue = queue;
    }


    public Queue<OperationLog> getQueue()
    {
        return queue;
    }
}

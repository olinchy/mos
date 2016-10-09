package com.zte.domain.transaction;

import com.zte.mos.exception.MOSException;

/**
 * Created by ccy on 7/16/16.
 */
public interface Transactional
{
    void onTimeOut(TransactionContext context) throws MOSException;
}

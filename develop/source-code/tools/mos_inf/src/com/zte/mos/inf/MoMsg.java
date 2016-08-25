package com.zte.mos.inf;

import java.io.Serializable;

public interface MoMsg extends Serializable
{
    MoCmds getCmd();

    Maybe<Integer> getTransactionID();

    void setTransId(Maybe<Integer> transId);

    String dn();

    String[] dns();

    void setDN(String dn);
}

package com.zte.mos.domain;

import java.util.List;

/**
 * Created by ccy on 2/25/16.
 */
public interface ReservedMoChecker
{
    String modelVersion();
    List<String> modelNames();
    boolean isReserved(ManagementObject mo);
}

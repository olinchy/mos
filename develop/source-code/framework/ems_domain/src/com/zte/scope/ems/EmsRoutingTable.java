package com.zte.scope.ems;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.NonvalidKeywordException;
import com.zte.scope.bundle.BundleDomain;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luoqingkai on 15-7-15.
 */
public class EmsRoutingTable {

    public static final EmsRoutingTable service = new EmsRoutingTable();

    private ConcurrentHashMap<String, BundleDomain> routingMap = new ConcurrentHashMap<String, BundleDomain>(23771);

    private EmsRoutingTable() {

    }

    public void addRoute(String neDN, BundleDomain bundleDomain) {
        routingMap.put(neDN, bundleDomain);
    }

    public void delRoute(String neDN) {
        routingMap.remove(neDN);
    }

    public BundleDomain getDestBundle(DN dn) throws NonvalidKeywordException {
        String neDN = dn.getHead("Ne");
        return routingMap.get(neDN);
    }



}

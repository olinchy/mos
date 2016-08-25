package com.zte.mos.service;

/**
 * Created by love on 16-1-9.
 * Register to EMS process
 */
@DomainUnique
public interface IRegService extends IExtService{
    // return the id of bundle
    String register(String selfURL, Regable obj) throws Exception;
}

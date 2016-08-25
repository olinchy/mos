package com.zte.mos.service;

import java.rmi.Remote;

@DomainUnique
public interface IOperationService extends IExtService {
    Remote getRemote();
}

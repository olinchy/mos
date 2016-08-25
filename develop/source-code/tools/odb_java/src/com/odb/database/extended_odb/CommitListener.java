package com.odb.database.extended_odb;

import java.util.ArrayList;

/**
 * Created by olinchy on 11/12/14 for MO_JAVA.
 */
public interface CommitListener<T>
{
    void notify(ArrayList<OperationUnit<T>> notiLst);
}

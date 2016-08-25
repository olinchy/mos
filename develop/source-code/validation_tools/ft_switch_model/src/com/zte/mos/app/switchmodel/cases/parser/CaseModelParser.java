package com.zte.mos.app.switchmodel.cases.parser;

import com.zte.mos.app.switchmodel.cases.env.CaseEnv;
import com.zte.mos.app.switchmodel.cases.model.CaseModel;

/**
 * Created by ccy on 5/26/16.
 */
public interface CaseModelParser
{
    CaseModel parse(CaseEnv env);
}

package com.zte.mos.app.switchmodel.cases.builder;

import com.zte.mos.app.switchmodel.cases.model.CaseModel;
import com.zte.mos.app.switchmodel.cases.env.CaseEnv;
import com.zte.mos.app.switchmodel.cases.parser.CaseModelParser;
import com.zte.mos.app.switchmodel.cases.parser.DefaultCaseModelParser;

/**
 * Created by ccy on 5/26/16.
 */
public class DefaultCaseBuilder
{
    private static CaseModelParser parser = new DefaultCaseModelParser();

    public static CaseModel buildCase(CaseEnv env)
    {
        return parser.parse(env);
    }
}

package com.zte.mos.app.switchmodel.cases.all;

import com.zte.mos.app.switchmodel.framework.ModelSwitchFt;
import com.zte.mos.app.switchmodel.output.ModelSwitchOutputter;
import com.zte.mos.message.Mo;
import com.zte.mos.type.Pair;
import com.zte.mos.util.Scan;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by ccy on 5/31/16.
 */
@RunWith(Parameterized.class)
public class ModelSwitchTraverseTest
{
    private final Class<ModelSwitchFt> clazz;
    private static final String outputFileName = "./test/com/zte/mos/app/switchmodel/cases/all/modelSwitchTraverseSummary";

    @BeforeClass
    public static void beforeDoModelSwitchTraverseTest()
    {
        File file = new File(outputFileName);
        if (file.exists())
        {
            file.delete();
        }
    }


    @AfterClass
    public static void afterDoModelSwitchTraverseTest()
    {

    }

    @Parameterized.Parameters
    public static Collection<Object[]> data()
    {
        Collection<Object[]> list  = new ArrayList<Object[]>();

        Set<Class<ModelSwitchFt>> set = Scan.getClasses("com.zte.mos.app.switchmodel.cases", ModelSwitchFt.class);

        for(Class<ModelSwitchFt> clazz : set)
        {
            list.add(new Object[]{clazz});
        }
        return list;
    }

    public ModelSwitchTraverseTest(Class<ModelSwitchFt> clazz)
    {
        this.clazz = clazz;
    }

    @Test
    public void doTest()
    {
        for(Method method : clazz.getDeclaredMethods())
        {
            if (method.isAnnotationPresent(Test.class))
            {
                try
                {
                    ModelSwitchFt ft = clazz.newInstance();
                    ft.prepareModelSwitch();
                    method.invoke(ft);
                    outPut(ft.getResult(), clazz.getName());
                    ft.unprepareModelSwitch();
                }
                catch (Throwable e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


    private void outPut(List<Map<String, List<Pair<Mo, Mo>>>> list, String caseName)
    {
        for(Map<String, List<Pair<Mo, Mo>>> map : list)
        {
            new ModelSwitchOutputter(caseName, outputFileName, map).output();
        }
    }

}

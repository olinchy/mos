package com.zte.mos.app.switchmodel.cases.NR8250.V2_V241.TEST;
import com.zte.mos.app.switchmodel.framework.ModelSwitchFt;
import com.zte.mos.exception.MOSException;
import org.junit.Test;
import java.io.IOException;

 public class TESTModelSwitchFt extends ModelSwitchFt
{
    @Test
    public void testModelSwitchFt() throws MOSException, IOException
    {
        doModelSwitchFt(new String[]{"nr8250",
                                     "v2",
                                     "nr8250",
                                     "v241",
                                     "nr8250",
                                     "/home/workspace/git_workspace/mos-ems/develop/source-code/validation_tools/ft_switch_model/./config/NR8250/V2_V241/TEST/OLD",
                                     "/home/workspace/git_workspace/mos-ems/develop/source-code/validation_tools/ft_switch_model/./config/NR8250/V2_V241/TEST/NEW",
                                     "./test/com/zte/mos/app/switchmodel/cases/NR8250/V2_V241/TEST/output/OLD",
                                     "./test/com/zte/mos/app/switchmodel/cases/NR8250/V2_V241/TEST/output/NEW"});}
}

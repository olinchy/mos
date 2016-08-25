package com.zte.mos.app.switchmodel.cases.NR8950.V2_V241.V2030310P04_2040110;
import com.zte.mos.app.switchmodel.framework.ModelSwitchFt;
import com.zte.mos.exception.MOSException;
import org.junit.Test;
import java.io.IOException;

 public class V2030310P04_2040110ModelSwitchFt extends ModelSwitchFt
{
    @Test
    public void testModelSwitchFt() throws MOSException, IOException
    {
        doModelSwitchFt(new String[]{"nr8950",
                                     "v2",
                                     "nr8950",
                                     "v241",
                                     "nr8950",
                                     "/home/workspace/git_workspace/mos-ems/develop/source-code/validation_tools/ft_switch_model/./config/NR8950/V2_V241/V2030310P04_2040110/OLD",
                                     "/home/workspace/git_workspace/mos-ems/develop/source-code/validation_tools/ft_switch_model/./config/NR8950/V2_V241/V2030310P04_2040110/NEW",
                                     "./test/com/zte/mos/app/switchmodel/cases/NR8950/V2_V241/V2030310P04_2040110/output/OLD",
                                     "./test/com/zte/mos/app/switchmodel/cases/NR8950/V2_V241/V2030310P04_2040110/output/NEW"});}
}

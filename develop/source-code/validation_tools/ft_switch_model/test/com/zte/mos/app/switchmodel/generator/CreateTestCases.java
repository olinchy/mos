package com.zte.mos.app.switchmodel.generator;

import com.zte.mos.app.switchmodel.cases.env.CaseEnv;
import com.zte.mos.app.switchmodel.cases.model.CaseModel;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.zte.mos.app.switchmodel.cases.builder.DefaultCaseBuilder.buildCase;

/**
 * Created by ccy on 3/22/16.
 */
public class CreateTestCases
{

    public CreateTestCases()
    {

    }
    @Test
    public void createCases()
    {
        create("NR8000TR");
        create("NR8250");
        create("NR8150");
        create("NR8120");
        create("NR8950");
    }

    private void create(String modelType)
    {
        File dir = new File("./config" + File.separator + modelType);
        File[] subDirs = dir.listFiles();

        for(File subDir : subDirs)
        {
            if (subDir.isDirectory()) //v1_v2
            {
                File[] files = subDir.listFiles();

                for(File file: files) //LACP
                {
                    String oldPath = file.getAbsolutePath() + File.separator + "OLD";
                    String newPath = file.getAbsolutePath() + File.separator + "NEW";
                    CaseEnv env = new CaseEnv(modelType, subDir.getName(), file.getName(), oldPath, newPath);

                    CaseModel model = buildCase(env);

                    if (model != null)
                    {
                        output(env, model);
                    }
                }
            }
        }
    }

    private void output(CaseEnv env, CaseModel model)
    {
        File outputCase  = new File("./test/com/zte/mos/app/switchmodel/cases/" + env.getNeType() + File.separator + env.getModelVersionHop() + File.separator + model.getScence());
        File outputDb = new File("./test/com/zte/mos/app/switchmodel/cases/" + env.getNeType() + File.separator + env.getModelVersionHop() + File.separator + model.getScence() + File.separator + "output");
        createDirs(outputCase);
        createDirs(outputDb);
        env.setCaseOutPut(outputCase.toString());
        env.setBerkleyDbOutPut(outputDb.toString());
        generateCase(env, model);
    }

    private void generateCase(CaseEnv env, CaseModel model)
    {
        String tmpl = skeleton();
        String fileName = model.getScence() + "ModelSwitchFt";
        tmpl = tmpl.replace("${NETYPE}", env.getNeType());
        tmpl = tmpl.replace("${VERSIONHOP}", env.getModelVersionHop());
        tmpl = tmpl.replace("${SCENCE}", model.getScence());
        tmpl = tmpl.replace("${TEST_CASE}", getCaseBody(env, model));
        tmpl = tmpl.replace("${CLASSNAME}", fileName);

        writeFile(env.getCaseOutPut() + "/" + fileName + ".java", tmpl);

    }

    private String getCaseBody(CaseEnv env, CaseModel model)
    {


        return    "  @Test\r\n"
                + "    public void testModelSwitchFt() throws MOSException, IOException\r\n"
                + "    {\r\n"
                + "        doModelSwitchFt(" + "new String[]{"
                + "\"" + model.getOldModelType()  + "\"" + "," + "\r\n"
                + "                                     \"" +model.getOldModelVersion() + "\"" + "," + "\r\n"
                + "                                     \"" +model.getNewModelType() + "\"" + "," + "\r\n"
                + "                                     \"" +model.getNewModelVersion() + "\"" +"," + "\r\n"
                + "                                     \"" +model.getNeType() + "\"" + "," + "\r\n"
                + "                                     \"" +model.getOldCfgPath() + "\"" +"," + "\r\n"
                + "                                     \"" +model.getNewCfgPath() + "\"" + "," + "\r\n"
                + "                                     \"" +env.getBerkleyDbOutPut() + "/OLD" + "\"" + "," + "\r\n"
                + "                                     \"" +env.getBerkleyDbOutPut() + "/NEW" + "\"" + "});"
                + "}";


    }


    private String createDirs(File dir)
    {
        if (dir.exists())
        {
            dir.delete();
        }
        dir.mkdirs();

        return dir.toString();
    }

    private String skeleton()
    {

        StringBuffer buffer = new StringBuffer();
        buffer.append("package com.zte.mos.app.switchmodel.cases.${NETYPE}.${VERSIONHOP}.${SCENCE};\r\n");
        buffer.append("import com.zte.mos.app.switchmodel.framework.ModelSwitchFt;\r\n");
        buffer.append("import com.zte.mos.exception.MOSException;\r\n");
        buffer.append("import org.junit.Test;\r\n");
        buffer.append("import java.io.IOException;\r\n");
        buffer.append("\r\n");
        buffer.append(" public class " + "${CLASSNAME}" + " extends ModelSwitchFt\r\n");
        buffer.append("{\r\n");
        buffer.append("  ${TEST_CASE}\r\n");
        buffer.append("}\r\n");
        return buffer.toString();
    }

    private void writeFile(String fileName, String content)
    {
        try
        {
            File file = new File(fileName);
            if (file.exists())
            {
                file.delete();
            }
            BufferedWriter br = new BufferedWriter(new FileWriter(fileName));
            br.write(content);
            br.flush();
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



}

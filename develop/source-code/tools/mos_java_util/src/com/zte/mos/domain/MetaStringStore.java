package com.zte.mos.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.annotation.MoChild;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.AttributeClass;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.type.Pair;
import com.zte.mos.util.Visitor;
import com.zte.mos.util.tools.JsonUtil;
import com.zte.mos.util.tools.Prop;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.zte.mos.type.Pair.pair;
import static com.zte.mos.util.basic.Logger.logger;
import static com.zte.mos.util.tools.JsonUtil.toObject;

/**
 * Created by olinchy on 7/9/14 for MO_JAVA.
 */
public class MetaStringStore
{
    private static String filePath = Prop.get("confpath") + File.separator + "model";
    private static final String[] types = new String[]{"enums", "mo", "object"};
    private HashMap<EntityName, HashMap<String, JsonNode>> nodes = new HashMap<EntityName, HashMap<String, JsonNode>>();
    private HashSet<String> moClassNames = new HashSet<String>();
    private HashMap<String, ArrayList<String>> extenders = new HashMap<String, ArrayList<String>>();
    private final Yaml yaml = new Yaml();

    private MetaStringStore() throws Exception
    {
        init(filePath);
    }

    public MetaStringStore(String path) throws Exception
    {
        init(path);
    }

    private HashMap<EntityName, EntityName> entityNameMap = new HashMap<EntityName, EntityName>();
    private HashMap<EntityName, HashMap<String, Class<?>>> nodesClasses = new HashMap<EntityName, HashMap<String, Class<?>>>();

    public Pair<EntityName, JsonNode> get(String modelName, String version, String className)
    {
        if (modelName.equals(""))
        {
            return null;
        }
        modelName = modelName.toLowerCase();
        version = version.toLowerCase();

        EntityName name = entityNameMap.get(new EntityName(modelName + "." + version, ""));

        JsonNode expected;
        if (name == null || (expected = nodes.get(name).get(className)) == null)
        {
            if (name != null && name.extend() != null)
            {
                return get(modelName, name.extend(), className);
            }
            name = entityNameMap.get(new EntityName(modelName, ""));
            expected = nodes.get(name).get(className);
            if (expected == null)
            {
                return get(name.parent(), version, className);
            }
            return pair(name, expected);
        }

        return pair(name, expected);
    }

    public Class<?> getClass(String modelName, String version, String className) throws MOSException
    {
        if (modelName.equals(""))
        {
            return null;
        }
        modelName = modelName.toLowerCase();
        version = version.toLowerCase();

        EntityName name = entityNameMap.get(new EntityName(modelName + "." + version, ""));

        Class<?> expected;
        if (name == null || (expected = nodesClasses.get(name).get(className)) == null)
        {
            if (name != null && name.extend() != null)
            {
                return getClass(modelName, name.extend(), className);
            }
            name = entityNameMap.get(new EntityName(modelName, ""));
            expected = nodesClasses.get(name).get(className);
            if (expected == null)
            {
                return getClass(name.parent(), version, className);
            }
            return expected;
        }

        return expected;
    }

    private void init(String filePath) throws Exception
    {
        for (String type : types)
        {
            load(new File(filePath + File.separator + type), "", nodes);
        }
        for (EntityName entityName : nodes.keySet())
        {
            entityNameMap.put(entityName, entityName);
        }
        initClasses();
    }

    private void initClasses()
    {
        try
        {
            for (EntityName entityName : nodes.keySet())
            {
                HashMap<String, JsonNode> map = nodes.get(entityName);
                HashMap<String, Class<?>> mapClass = new HashMap<String, Class<?>>();
                for (Map.Entry<String, JsonNode> entry : map.entrySet())
                {
                    MoMetaClass meta = JsonUtil.toObject(entry.getValue().toString(), MoMetaClass.class);
                    initRelationOfSuperClass(meta, extenders);
                    if (meta.className == null)
                        continue;
                    meta.forName(entityName.getFullName());
                    mapClass.put(meta.type.getSimpleName(), meta.type);
                    for (Field field : meta.type.getFields())
                    {
                        if (field.isAnnotationPresent(MoChild.class) && field.getType().getSimpleName().startsWith(
                                "Group"))
                        {
                            mapClass.put(field.getType().getSimpleName(), field.getType());
                        }
                    }
                }
                if (nodesClasses.containsKey(entityName))
                    nodesClasses.get(entityName).putAll(mapClass);
                else
                    nodesClasses.put(entityName, mapClass);
            }
        }
        catch (Throwable e)
        {
            logger(this.getClass()).warn("init classes caught exception", e);
        }
    }

    private void initRelationOfSuperClass(MoMetaClass meta, HashMap<String, ArrayList<String>> extenders)
    {
        if (meta.baseClass != null && !meta.baseClass.equals(""))
        {
            if (!extenders.containsKey(meta.baseClass))
            {
                extenders.put(meta.baseClass, new ArrayList<String>());
            }
            extenders.get(meta.baseClass).add(meta.className);
        }
    }

    private void load(File under, final String parentPath, final HashMap<EntityName, HashMap<String, JsonNode>> nodes)
    {
        under.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                if (pathname.isDirectory())
                {
                    EntityName entityName = new EntityName(pathname.getName(), parentPath);
                    if (!pathname.getName().matches("v[\\d]+"))
                    {
                        // version folder cannot has any more sub entities
                        // is entity
                        load(pathname, parentPath + '.' + pathname.getName(), nodes);
                    }
                    else
                    {
                        String superVersion = getSuperVersion(pathname);
                        entityName = new EntityName(
                                parentPath + "." + pathname.getName());
                        entityName.setSuperVersion(superVersion);
                    }
                    HashMap<String, JsonNode> list = readYmlUnderThis(pathname);
                    if (nodes.containsKey(entityName))
                    {
                        nodes.get(entityName).putAll(list);
                    }
                    else
                    {
                        nodes.put(entityName, list);
                    }
                }
                return true;
            }

            private String getSuperVersion(File pathname)
            {
                File extendsFile = new File(pathname + File.separator + "extends");
                if (extendsFile.exists())
                {
                    BufferedReader reader = null;
                    try
                    {
                        reader = new BufferedReader(new FileReader(extendsFile));
                        String superVersion = reader.readLine();

                        return superVersion;
                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally {
                        if(reader != null){
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                return null;
            }

            private HashMap<String, JsonNode> readYmlUnderThis(File path)
            {
                final HashMap<String, JsonNode> list = new HashMap<String, JsonNode>();
                path.listFiles(new FileFilter()
                {
                    @Override
                    public boolean accept(File pathname)
                    {
                        if (pathname.isFile() && pathname.getName().endsWith(".yml"))
                        {
                            FileReader reader = null;
                            try
                            {
                                String className;
                                reader = new FileReader(pathname);
                                list.put(
                                        className = pathname.getName().replace(".yml", ""),
                                        JsonUtil.toNode(yaml.load(reader)));
                                moClassNames.add(className);
                            }
                            catch (FileNotFoundException e)
                            {
                                e.printStackTrace();
                            }
                            catch (MOSException e)
                            {
                                e.printStackTrace();
                            }
                            finally {
                                if(reader != null){
                                    try {
                                        reader.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        return true;
                    }
                });

                return list;
            }
        });
    }

    public JsonNode get(EntityName entityName, String simpleName)
    {
        return nodes.get(entityName).get(simpleName);
    }

    public void visit(Visitor<Pair<EntityName, JsonNode>> visitor) throws MOSException
    {
        for (Map.Entry<EntityName, HashMap<String, JsonNode>> entityNameHashMapEntry : nodes.entrySet())
        {
            for (Map.Entry<String, JsonNode> nodeEntry : entityNameHashMapEntry.getValue().entrySet())
            {
                visitor.visit(pair(entityNameHashMapEntry.getKey(), nodeEntry.getValue()));
            }
        }
    }

    public boolean isIn(String who)
    {
        return moClassNames.contains(who);
    }

    public MoMetaClass getMeta(String modelName, String version, String className) throws MOSException
    {
        Pair<EntityName, JsonNode> pair = this.get(modelName, version, className);
        if (pair == null)
        {
            return null;
        }

        MoMetaClass meta = JsonUtil.toObject(pair.second().toString(), MoMetaClass.class);
        meta.forName(pair.first().getFullName());

        // add meta of all enums
        for (AttributeClass attribute : meta.attributes)
        {
            if (attribute.type.endsWith("Enum"))
            {
                Pair<EntityName, JsonNode> enumNode = this.get(modelName, version, attribute.type);
                if (enumNode != null)
                {
                    meta.enums.put(attribute.name, toObject(enumNode.second().get("enum").toString(), Object.class));
                }
            }
            else if (attribute.enums != null && attribute.enums.size() > 0)
            {
                meta.enums.put(attribute.name, attribute.enums);
            }
        }
        // end

        meta.extenders = this.extenders.get(meta.className);

        meta.setModelVersion(version);
        meta.setModelName(modelName);
        return meta;
    }
}

package com.zte.mos.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 3/19/15 for mosjava.
 */
public class MoMetaClass implements Serializable
{
    public static String Package_Prefix = "com.zte.mos.autogen";
    @JsonInclude(NON_NULL)
    public String className;
    @JsonInclude(NON_NULL)
    public int tid;
    @JsonInclude(NON_NULL)
    public boolean isAbstract;
    @JsonInclude(NON_NULL)
    public String baseClass;
    @JsonInclude(NON_NULL)
    public String derivedFrom;
    @JsonInclude(NON_NULL)
    public String derivedBy;
    @JsonInclude(NON_NULL)
    public String msgMode;
    @JsonInclude(NON_NULL)
    public String imagedProtocol;
    @JsonInclude(NON_NULL)
    public String mosVersion; // msgVersion like 1.2f 1.1f
    @JsonInclude(NON_EMPTY)
    public ArrayList<ReferenceClass> reference = new ArrayList<ReferenceClass>();
    @JsonInclude(NON_EMPTY)
    public ArrayList<AttributeClass> attributes = new ArrayList<AttributeClass>();
    @JsonInclude(NON_EMPTY)
    public ArrayList<ChildrenClass> children = new ArrayList<ChildrenClass>();
    @JsonInclude(NON_NULL)
    public ActionListClass actions;
    @JsonInclude(NON_NULL)
    public ArrayList<String> extenders = new ArrayList<String>();
    @JsonInclude(NON_NULL)
    @JsonProperty("enum")
    public LinkedHashMap<String, Object> enums = new LinkedHashMap<String, Object>();
    @JsonIgnore
    public Class<?> type;
    @JsonIgnore
    public String modelName = ""; // like nr8250 nr8960A
    @JsonIgnore
    public String modelVersion = ""; // like v242 v241

    @JsonIgnore
    public AttributeClass getAttr(String name)
    {
        for (AttributeClass attribute : attributes)
        {
            if (attribute.name.equals(name))
            {
                return attribute;
            }
        }
        return null;
    }

    public void forName(String path)
    {
        if (className == null)
            return;
        String name = Package_Prefix + path + "." + className;
        try
        {
            this.type = Class.forName(name);
            String[] names = path.split("\\.");
            if (names[names.length - 1].matches("v[\\d]+"))
            {
                this.modelName = names[names.length - 2];
                this.modelVersion = names[names.length - 1];
            }
            else
            {
                this.modelName = names[names.length - 1];
            }
        }
        catch (Throwable e)
        {
            logger(this.getClass()).warn("instance " + name + " failed!", e);
        }
    }

    public <T> T instance(Class<T> clazz) throws IllegalAccessException, InstantiationException
    {
        return (T) this.type.newInstance();
    }

    public void setModelVersion(String modelVersion)
    {
        this.modelVersion = modelVersion;
    }

    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }
}

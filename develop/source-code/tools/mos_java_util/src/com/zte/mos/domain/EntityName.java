package com.zte.mos.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by olinchy on 16-3-18.
 */
public class EntityName
{
    public EntityName(String entityName, String parentName)
    {
        this.entityName = entityName;
        this.parentName = parentName;
        this.fullName = parentName + "." + entityName;
    }

    public EntityName(String fullName)
    {
        this.fullName = fullName;
        Pattern pattern = Pattern.compile("(.*)\\.([^\\.]+)\\.(v[\\d]+)");
        Matcher matcher = pattern.matcher(fullName);
        if (matcher.matches())
        {
            this.parentName = matcher.group(1) + "." + matcher.group(2);
            this.entityName = matcher.group(2) + "." + matcher.group(3);
        }
        else
        {
            int index = fullName.lastIndexOf(".");
            this.parentName = fullName.substring(0, index);
            this.entityName = fullName.substring(index + 1);
        }
    }

    private String parentName = "";
    private String entityName = "";
    private String fullName = "";
    private String superVersion = null;

    @Override
    public String toString()
    {
        return "EntityName{" +
                "parentName='" + parentName + '\'' +
                ", entityName='" + entityName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityName that = (EntityName) o;

        return (entityName != null ? entityName.equals(
                that.entityName) : that.entityName == null) || (fullName != null ? fullName.equals(
                that.fullName) : that.fullName == null);
    }

    @Override
    public int hashCode()
    {
        return entityName != null ? entityName.hashCode() : 0;
    }

    public boolean hasParent()
    {
        return !parentName.equals("");
    }

    public String parent()
    {
        return parentName.substring(parentName.lastIndexOf(".") + 1);
    }

    public String getFullName()
    {
        return fullName;
    }

    public String extend()
    {
        return superVersion;
    }

    public void setSuperVersion(String superVersion)
    {
        this.superVersion = superVersion;
    }
}

package com.odb.ut.berkeleydb;

import com.odb.database.BerkeleyDBDPLIndexer;
import com.odb.database.BerkeleyDBIndexer;
import com.odb.database.Key;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;
import com.zte.mos.exception.MOSException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by olinchy on 15-9-30.
 */
@Entity
public class Person
{
    public Person()
    {
    }

    public Person(int id, String name, String city, String company)
    {
        this.id = id;
        this.name = name;
        this.city = city;
        this.company = company;
    }

    @PrimaryKey
    public int id;
    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    public String name;
    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    public String city;
    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    public String company;
    public ArrayList<State> states = new ArrayList<State>();

    public static BerkeleyDBIndexer<Integer, Person> createIndex()
    {
        return new BerkeleyDBDPLIndexer<Integer, Person>()
        {
            @Override
            protected String dbName()
            {
                return "person";
            }

            @Override
            protected PrimaryIndex<Integer, Person> createPrimaryIndex()
            {
                this.primaryIndex = store.getPrimaryIndex(Integer.class, Person.class);
                return primaryIndex;
            }

            @Override
            protected HashMap<String, SecondaryIndex> createSecondIndexes()
            {
                HashMap<String, SecondaryIndex> res = new HashMap<String, SecondaryIndex>();

                SecondaryIndex<String, Integer, Person> nameKey =
                        store.getSecondaryIndex(createPrimaryIndex(), String.class, "name");
                res.put("nameIndex", nameKey);

                SecondaryIndex<String, Integer, Person> companyKey =
                        store.getSecondaryIndex(createPrimaryIndex(), String.class, "company");
                res.put("companyIndex", companyKey);

                SecondaryIndex<String, Integer, Person> cityKey =
                        store.getSecondaryIndex(createPrimaryIndex(), String.class, "city");
                res.put("cityIndex", cityKey);
                return res;
            }
        };
    }

    @Override
    public String toString()
    {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}

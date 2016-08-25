/*-
 *
 *  This file is part of Oracle Berkeley DB Java Edition
 *  Copyright (C) 2002, 2015 Oracle and/or its affiliates.  All rights reserved.
 *
 *  Oracle Berkeley DB Java Edition is free software: you can redistribute it
 *  and/or modify it under the terms of the GNU Affero General Public License
 *  as published by the Free Software Foundation, version 3.
 *
 *  Oracle Berkeley DB Java Edition is distributed in the hope that it will be
 *  useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License in
 *  the LICENSE file along with Oracle Berkeley DB Java Edition.  If not, see
 *  <http://www.gnu.org/licenses/>.
 *
 *  An active Oracle commercial licensing agreement for this product
 *  supercedes this license.
 *
 *  For more information please contact:
 *
 *  Vice President Legal, Development
 *  Oracle America, Inc.
 *  5OP-10
 *  500 Oracle Parkway
 *  Redwood Shores, CA 94065
 *
 *  or
 *
 *  berkeleydb-info_us@oracle.com
 *
 *  [This line intentionally left blank.]
 *  [This line intentionally left blank.]
 *  [This line intentionally left blank.]
 *  [This line intentionally left blank.]
 *  [This line intentionally left blank.]
 *  [This line intentionally left blank.]
 *  EOF
 *
 */
package com.odb.ut.berkeleydb;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.Persistent;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.sleepycat.persist.model.DeleteAction.NULLIFY;
import static com.sleepycat.persist.model.Relationship.*;

public class PersonExample_My
{
    private Environment env;
    private EntityStore store;
    private PersonAccessor dao;

    private PersonExample_My(File envHome)
            throws DatabaseException
    {

        /* Open a transactional Berkeley DB engine environment. */
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        envConfig.setTransactional(true);
        env = new Environment(envHome, envConfig);

        /* Open a transactional entity store. */
        StoreConfig storeConfig = new StoreConfig();
        storeConfig.setAllowCreate(true);
        storeConfig.setTransactional(true);
        store = new EntityStore(env, "PersonStore", storeConfig);

        /* Initialize the data access object. */
        dao = new PersonAccessor(store);
    }

    public static void main(String[] args)
            throws DatabaseException
    {

        if (args.length != 2 || !"-h".equals(args[0]))
        {
            System.err.println
                    (
                            "Usage: java " + PersonExample_My.class.getName() +
                                    " -h <envHome>");
            System.exit(2);
        }
        PersonExample_My example = new PersonExample_My(new File(args[1]));
        example.run();
        example.close();
    }

    private void run()
            throws DatabaseException
    {

        /*
         * Add a parent and two children using the Person primary index.
         * Specifying a non-null parentSsn adds the child Person to the
         * sub-index of children for that parent key.
         */
//        dao.personBySsn.put
//                (new Person("Bob Smith", "111-11-1111", null));
        int times = 100000000;
        int taskCount = 20;
        Date date = new Date();

//         TaskObserver observer = new TaskObserver(taskCount);
//
//        for (int i = 0; i < taskCount; i++)
//        {
//            new PutTask(times / taskCount * i, times / taskCount, observer, dao).start();
//        }
//
//        while (!observer.isFinished())
//        {
//            try
//            {
//                Thread.sleep(10);
//            }
//            catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(
//                "insert " + String.valueOf(times) + " mo in " + String.valueOf(
//                        new Date().getTime() - date.getTime()) + " ms");

        /* Print the children of a parent using a sub-index and a cursor. */
//        EntityCursor<Person> children =
//            dao.personByParentSsn.subIndex("111-11-1111").entities();
//        try {
//            for (Person child : children) {
//                System.out.println(child.ssn + ' ' + child.name);
//            }
//        } finally {
//            children.close();
//        }
        for (int i = 0; i < 10000; i++)
        {
            int randomNumber = new Random(times).nextInt();
            long nano = System.nanoTime();
            Person person = dao.personBySsn.get(String.valueOf(randomNumber));
            System.out.println(
                    "locate random mo by index in " + String.valueOf(
                            System.nanoTime() - nano) + " ns");
            assert person != null;
        }
    }

    private void close()
            throws DatabaseException
    {

        store.close();
        env.close();
    }

    /* An entity class. */
    @Entity
    static class Person
    {
        @PrimaryKey
        String ssn;
        String name;
        Address address;
        @SecondaryKey(relate = MANY_TO_ONE, relatedEntity = Person.class)
        String parentSsn;
        @SecondaryKey(relate = ONE_TO_MANY)
        Set<String> emailAddresses = new HashSet<String>();
        @SecondaryKey(relate = MANY_TO_MANY,
                relatedEntity = Employer.class,
                onRelatedEntityDelete = NULLIFY)
        Set<Long> employerIds = new HashSet<Long>();

        Person(String name, String ssn, String parentSsn)
        {
            this.name = name;
            this.ssn = ssn;
            this.parentSsn = parentSsn;
        }

        private Person()
        {
        } // For deserialization
    }

    /* Another entity class. */
    @Entity
    static class Employer
    {
        @PrimaryKey(sequence = "ID")
        long id;
        @SecondaryKey(relate = ONE_TO_ONE)
        String name;
        Address address;

        Employer(String name)
        {
            this.name = name;
        }

        private Employer()
        {
        } // For deserialization
    }

    /* A persistent class used in other classes. */
    @Persistent
    static class Address
    {
        String street;
        String city;
        String state;
        int zipCode;

        private Address()
        {
        } // For deserialization
    }

    /* The data accessor class for the entity model. */
    static class PersonAccessor
    {
        /* Person accessors */
        PrimaryIndex<String, Person> personBySsn;
        SecondaryIndex<String, String, Person> personByParentSsn;
        SecondaryIndex<String, String, Person> personByEmailAddresses;
        SecondaryIndex<Long, String, Person> personByEmployerIds;
        /* Employer accessors */
        PrimaryIndex<Long, Employer> employerById;
        SecondaryIndex<String, Long, Employer> employerByName;

        /* Opens all primary and secondary indices. */
        public PersonAccessor(EntityStore store)
                throws DatabaseException
        {

            personBySsn = store.getPrimaryIndex(
                    String.class, Person.class);

            personByParentSsn = store.getSecondaryIndex(
                    personBySsn, String.class, "parentSsn");

            personByEmailAddresses = store.getSecondaryIndex(
                    personBySsn, String.class, "emailAddresses");

            personByEmployerIds = store.getSecondaryIndex(
                    personBySsn, Long.class, "employerIds");

            employerById = store.getPrimaryIndex(
                    Long.class, Employer.class);

            employerByName = store.getSecondaryIndex(
                    employerById, String.class, "name");
        }
    }
}

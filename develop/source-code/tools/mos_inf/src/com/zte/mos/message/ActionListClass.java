package com.zte.mos.message;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Created by olinchy on 4/22/15 for ems-mos.
 */
@JsonInclude(NON_NULL)
public class ActionListClass implements Serializable
{
    public ArrayList<ActionClass> customs = new ArrayList<ActionClass>();

}

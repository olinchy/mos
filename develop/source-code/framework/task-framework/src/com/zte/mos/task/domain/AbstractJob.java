package com.zte.mos.task.domain;

import java.util.List;

/**
 * Created by luoqingkai on 15-10-27.
 */
public abstract class AbstractJob implements IJob{
    Integer doneTaskNum = 0;
    List<AbstractJobTask> tasks;

    void taskDone(){
        synchronized (doneTaskNum){
            doneTaskNum++;
        }
    }

    public void addTask(AbstractJobTask task){
        task.setJob(this);
        tasks.add(task);
    }

    abstract List<AbstractJobTask> next();
}

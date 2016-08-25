package com.zte.container.ext.diff;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class V11SyncDiffThread extends Thread{

    private static List<V11SyncDiffTask> taskList = new LinkedList<V11SyncDiffTask>();
    private static V11SyncDiffThread runner = new V11SyncDiffThread();
    static {
        runner.start();
    }

    @Override
    public void run() {
//        synchronized (taskList){
//            while(!taskList.isEmpty()){
//                V11SyncDiffTask task = taskList.remove(0);
//                if (task != null){
//                    task.run();
//                }
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void addTask(V11SyncDiffTask task){
//        synchronized (taskList){
//            taskList.add(task);
//        }
    }

    public static void removeTaskOf(String targetId){
//        synchronized (taskList){
//            Iterator<V11SyncDiffTask> it = taskList.iterator();
//            while(it.hasNext()){
//                V11SyncDiffTask task = it.next();
//                if (task.belongTo(targetId)){
//                    it.remove();
//                }
//            }
//        }
    }
}

package com.zte.mos.task.domain;

/**
 * Created by luoqingkai on 15-10-27.
 * All of the user defined tasks should extends this Class
 */
public abstract class AbstractJobTask implements IJobTask {

    protected AbstractJob job;
    private TaskState state = TaskState.READY;
    private AbstractStep currentStep;

    public AbstractJobTask(AbstractStep initStep) throws InstantiationException {
        if (initStep == null){
            throw new InstantiationException("null parameter");
        }
        this.currentStep = initStep;
    }

    final void setJob(AbstractJob job){
        this.job = job;
    }

    public final TaskState getState(){
        return state;
    }



    @Override
    public final void run() {
        state = TaskState.RUNNING;
        currentStep.run();
        currentStep = currentStep.next();

        if (currentStep == null){
            done();
        }else{
            ready();
        }
    }

    private void ready(){
        state = TaskState.READY;
    }

    private void done(){
        state = TaskState.DONE;
        if (job != null){
            job.taskDone();
        }
    }

}

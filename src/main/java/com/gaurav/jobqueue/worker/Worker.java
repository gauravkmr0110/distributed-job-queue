package com.gaurav.jobqueue.worker;

import com.gaurav.jobqueue.model.Job;
import com.gaurav.jobqueue.model.JobStatus;
import com.gaurav.jobqueue.service.JobExecutionService;

import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable{
    private final BlockingQueue<Job> queue;
    private final String workerName;
    private final JobExecutionService jobExecutionService;

    private static final int MAX_RETRIES = 3;

    public Worker(BlockingQueue<Job>queue, String name, JobExecutionService service){
        this.queue = queue;
        this.workerName = name;
        this.jobExecutionService = service;
    }

    @Override
    public void run(){
        while(true){
            try {
                Job job = queue.take();
                System.out.println(workerName + " picked job " + job.getId());
                execute(job);
            }
            catch(InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void execute(Job job){
        try{
            // job logic
            jobExecutionService.markRunning(job);
            System.out.println(workerName + " processing job "+ job.getId() + " | attempt " + (job.getRetryCount()+1));
            // simulate failure
            if(Math.random() < 0.9){
                throw new RuntimeException("Simulation failure");
            }
            Thread.sleep(1000); // real work simulation
            jobExecutionService.markSuccess(job);
            System.out.println(workerName + " completed job "+ job.getId() + " | attempt " + (job.getRetryCount()+1));

        }
        catch(Exception e){

            if(job.getRetryCount() < MAX_RETRIES){
                job.incrementRetry();
                jobExecutionService.markPending(job);
                System.out.println(workerName + " retrying job "+ job.getId() + " | attempt " + (job.getRetryCount()+1));

                try{
                    queue.put(job);
                }
                catch(Exception ex){
                    Thread.currentThread().interrupt();
                }
            }
            else{
                jobExecutionService.markFailed(job);
                System.out.println(workerName + " failed job "+ job.getId());
            }
        }
    }
}

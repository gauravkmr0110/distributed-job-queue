package com.gaurav.jobqueue.model;

public class Job {
    private final int id;
    private final String type;
    private final String payload;

    private JobStatus status;
    private int retryCount;

    public Job(int id, String type, String payload){
        this.id = id;
        this.type = type;
        this.payload = payload;
        this.status = JobStatus.PENDING;
        this.retryCount = 0;
    }

    public int getId(){
        return id;
    }

    public String getType(){
        return type;
    }

    public String getPayload() {
        return payload;
    }

    public JobStatus getStatus(){
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public int getRetryCount() {
        return retryCount;
    }
    public void incrementRetry(){
        this.retryCount++;
    }
}


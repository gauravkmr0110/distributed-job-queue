package com.gaurav.jobqueue.model;
import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private  String type;
    private  String payload;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private int retryCount;

    public Job(){};

    public Job(String type, String payload){
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


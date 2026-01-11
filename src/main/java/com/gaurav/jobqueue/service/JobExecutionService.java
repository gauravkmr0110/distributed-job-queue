package com.gaurav.jobqueue.service;

import com.gaurav.jobqueue.model.Job;
import com.gaurav.jobqueue.model.JobStatus;
import com.gaurav.jobqueue.repository.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class JobExecutionService {
    private final JobRepository jobRepository;

    public JobExecutionService(JobRepository repo) {
        this.jobRepository = repo;
    }

    public void markRunning(Job job){
        job.setStatus(JobStatus.RUNNING);
        jobRepository.save(job);
    }

    public void markSuccess(Job job){
        job.setStatus(JobStatus.SUCCESS);
        jobRepository.save(job);
    }

    public void markPending(Job job){
        job.setStatus(JobStatus.PENDING);
        jobRepository.save(job);
    }

    public void markFailed(Job job){
        job.setStatus(JobStatus.FAILED);
        jobRepository.save(job);
    }
}

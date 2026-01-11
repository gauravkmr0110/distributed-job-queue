package com.gaurav.jobqueue.service;

import com.gaurav.jobqueue.model.Job;
import com.gaurav.jobqueue.model.JobStatus;
import com.gaurav.jobqueue.repository.JobRepository;
import com.gaurav.jobqueue.worker.Worker;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
public class JobService {

    private final BlockingQueue<Job> queue = new LinkedBlockingQueue<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private final JobRepository jobRepository;
    private final JobExecutionService jobExecutionService;

    public JobService(JobRepository jobRepository,JobExecutionService jobExecutionService){
        this.jobRepository = jobRepository;
        this.jobExecutionService = jobExecutionService;
    }

    @PostConstruct
    public void startWorkers() {
        for(int i=0; i<3; i++){
            executor.submit(new Worker(queue,"worker -"+i, jobExecutionService));
        }
        recoverJobs();
    }

    public void enqueueJob(Job job){
        try{
            job.setStatus(JobStatus.PENDING);
            Job saved = jobRepository.save(job);
            queue.put(saved);
        }
        catch(InterruptedException exception){
            Thread.currentThread().interrupt();
        }

    }

    public Job get(int id){
        return jobRepository.findById(id).orElse(null);
    }

    public void recoverJobs(){
        // Recover pending jobs
        List<Job> pendingJobs = jobRepository.findByStatus(JobStatus.PENDING);
        for(Job job : pendingJobs){
            try{
                queue.put(job);
            }
            catch(InterruptedException exception){
                Thread.currentThread().interrupt();
            }
        }

        // Recover running jobs

        List<Job> runningJobs = jobRepository.findByStatus(JobStatus.RUNNING);
        for(Job job : runningJobs){
            job.setStatus(JobStatus.PENDING);
            jobRepository.save(job);
            try{
                queue.put(job);
            }
            catch(InterruptedException exception){
                Thread.currentThread().interrupt();
            }
        }

    }

}

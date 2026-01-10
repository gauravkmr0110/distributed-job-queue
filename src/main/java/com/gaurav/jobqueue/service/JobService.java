package com.gaurav.jobqueue.service;

import com.gaurav.jobqueue.model.Job;
import com.gaurav.jobqueue.worker.Worker;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class JobService {

    private final BlockingQueue<Job> queue = new LinkedBlockingQueue<>();
    private final ConcurrentHashMap<Integer,Job> jobStore = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    @PostConstruct
    public void startWorkers() {
        for(int i=0; i<3; i++){
            executor.submit(new Worker(queue,"worker -"+i));
        }
    }

    public void submit(Job job) throws InterruptedException{
        jobStore.put(job.getId(),job);
        queue.put(job);
    }

    public Job get(int id){
        return jobStore.get(id);
    }

}

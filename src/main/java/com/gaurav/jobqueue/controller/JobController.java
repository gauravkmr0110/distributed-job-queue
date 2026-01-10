package com.gaurav.jobqueue.controller;

import com.gaurav.jobqueue.model.Job;
import com.gaurav.jobqueue.service.JobService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService){
        this.jobService = jobService;
    }

    @PostMapping
    public String submitJob(@RequestBody Job job) throws InterruptedException{
        jobService.submit(job);
        return "Job submitted with id " + job.getId();
    }

    @GetMapping("/{id}")
    public Job getJob(@PathVariable int id) {
        return jobService.get(id);
    }

}


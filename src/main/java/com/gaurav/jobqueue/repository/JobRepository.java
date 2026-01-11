package com.gaurav.jobqueue.repository;

import com.gaurav.jobqueue.model.Job;
import com.gaurav.jobqueue.model.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Integer> {

    List<Job> findByStatus(JobStatus status);
}

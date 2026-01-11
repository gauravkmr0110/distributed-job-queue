A Redis-backed distributed job processing system built using Java and Spring Boot, implementing the producer–consumer pattern to handle asynchronous background tasks with support for concurrent workers, retries, and failure-safe processing.

This project demonstrates how to design and implement a simple yet scalable job queue system where:

- Producers submit jobs

- Jobs are stored in a centralized queue

- Multiple worker processes consume and process jobs concurrently

- Failures are handled safely with retries

### Job Processing Flow: 

- Producer submits a job to job-queue

- Worker atomically moves the job to processing-queue

- Job is processed by a worker thread

- On success → job is removed from processing-queue

- On failure → job is retried up to a max limit

- After max retries → job is moved to failed-queue

### Tech Stack

- Java

- Spring Boot

- Redis

- Spring Data Redis

- Maven

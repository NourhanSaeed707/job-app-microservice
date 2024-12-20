package com.example.jobms.job.service;
import com.example.jobms.job.model.Job;
import java.util.List;

public interface JobService {
    List<Job> findAll();
    void create(Job job);
    Job getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean update(Long id, Job updatedJob);
}

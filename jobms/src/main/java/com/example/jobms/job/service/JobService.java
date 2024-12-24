package com.example.jobms.job.service;
import com.example.jobms.job.dto.JobWithCompanyDTO;
import com.example.jobms.job.model.Job;
import java.util.List;

public interface JobService {
    List<JobWithCompanyDTO> findAll();
    void create(Job job);
    Job getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean update(Long id, Job updatedJob);
}

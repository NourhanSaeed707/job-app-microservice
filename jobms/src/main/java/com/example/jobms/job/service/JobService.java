package com.example.jobms.job.service;
import com.example.jobms.job.dto.JobDTO;
import com.example.jobms.job.model.Job;
import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void create(Job job);
    JobDTO getJobById(Long id);
    boolean deleteJobById(Long id);
    boolean update(Long id, Job updatedJob);
}

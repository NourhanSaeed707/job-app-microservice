package com.example.jobms.job.service.Impl;
import com.example.jobms.job.external.Company;
import com.example.jobms.job.model.Job;
import com.example.jobms.job.repository.JobRepository;
import com.example.jobms.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<Job> findAll() {
        RestTemplate restTemplate = new RestTemplate();
        Company company = restTemplate.getForObject("http://localhost:8081/companies/1", Company.class );
        System.out.println("Company: " + company.getName());
        System.out.println("Company: " + company.getId());
        return jobRepository.findAll();
    }

    @Override
    public void create(Job job) {
       jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
       return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
       try {
           jobRepository.deleteById(id);
           return true;
       }
       catch (Exception e) {
           return false;
       }
    }

    @Override
    public boolean update(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        System.out.println("find by id");
        if(jobOptional.isPresent()) {
            System.out.println("inside condition");
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}

package com.example.jobms.job.service.Impl;
import com.example.jobms.job.dto.JobWithCompanyDTO;
import com.example.jobms.job.external.Company;
import com.example.jobms.job.mapper.JobMapper;
import com.example.jobms.job.model.Job;
import com.example.jobms.job.repository.JobRepository;
import com.example.jobms.job.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOS = new ArrayList<>();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobWithCompanyDTO convertToDto(Job job) {
//        RestTemplate restTemplate = new RestTemplate();
        Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class );
        JobWithCompanyDTO jobWithCompanyDTO = JobMapper.mapToJobWithCompanyDto(job, company);
        jobWithCompanyDTO.setCompany(company);
        return jobWithCompanyDTO;
    }

    @Override
    public void create(Job job) {
       jobRepository.save(job);
    }

    @Override
    public JobWithCompanyDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);
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

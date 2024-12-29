package com.example.jobms.job.service.Impl;
import com.example.jobms.job.clients.CompanyClient;
import com.example.jobms.job.clients.ReviewClient;
import com.example.jobms.job.dto.JobDTO;
import com.example.jobms.job.external.Company;
import com.example.jobms.job.external.Review;
import com.example.jobms.job.mapper.JobMapper;
import com.example.jobms.job.model.Job;
import com.example.jobms.job.repository.JobRepository;
import com.example.jobms.job.service.JobService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    private CompanyClient companyClient;
    private ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobWithCompanyDTOS = new ArrayList<>();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobDTO convertToDto(Job job) {
//        RestTemplate restTemplate = new RestTemplate();
//        Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class );
        Company company = companyClient.getCompany(job.getCompanyId());

        // This is used to get list of reviews not just object.
//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
//                "http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {});
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
//        List<Review> reviews = reviewResponse.getBody();
        JobDTO jobWithCompanyDTO = JobMapper.mapToJobWithCompanyDto(job, company, reviews);
        return jobWithCompanyDTO;
    }

    @Override
    public void create(Job job) {
       jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
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

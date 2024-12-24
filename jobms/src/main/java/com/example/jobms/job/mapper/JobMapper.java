package com.example.jobms.job.mapper;
import com.example.jobms.job.dto.JobWithCompanyDTO;
import com.example.jobms.job.external.Company;
import com.example.jobms.job.model.Job;

public class JobMapper {
    public static JobWithCompanyDTO mapToJobWithCompanyDto(Job job, Company company) {
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setCompany(company);
        return jobWithCompanyDTO;
    }
}

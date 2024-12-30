package com.example.companyms.company.service.Impl;
import com.example.companyms.company.clients.ReviewClient;
import com.example.companyms.company.dto.ReviewMessage;
import com.example.companyms.company.model.Company;
import com.example.companyms.company.repository.CompanyRepository;
import com.example.companyms.company.service.CompanyService;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(ReviewClient reviewClient) {
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    @Override
    public boolean update(Company updatedCompany, Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isPresent()) {
            Company company = companyOptional.get();
            company.setDescription(updatedCompany.getDescription());
            company.setName(updatedCompany.getName());
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public void create(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        if(companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCompany(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage.getDescription());
        Company company = companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(()-> new NotFoundException("Company not found " + reviewMessage.getCompanyId()));
        double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }
}

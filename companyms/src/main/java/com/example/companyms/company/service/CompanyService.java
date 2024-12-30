package com.example.companyms.company.service;
import com.example.companyms.company.dto.ReviewMessage;
import com.example.companyms.company.model.Company;
import java.util.List;

public interface CompanyService {
    List<Company> getAll();
    boolean update(Company updatedCompany, Long id);
    void create(Company company);
    boolean deleteCompanyById(Long id);
    Company getCompanyById(Long id);

    void updateCompany(ReviewMessage reviewMessage);
}

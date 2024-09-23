package com.mrc.chat.serviceimpl;

import com.mrc.chat.dto.CompanyDto;
import com.mrc.chat.model.Company;
import com.mrc.chat.repository.CompanyRepository;
import com.mrc.chat.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    @Override
    public Company createCompany(CompanyDto companydto) {
        return null;
    }

    @Override
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }
}

package com.mrc.chat.service;

import com.mrc.chat.dto.CompanyDto;
import com.mrc.chat.model.Company;

public interface CompanyService {

    public Company createCompany(CompanyDto companydto);

    public Company saveCompany(Company company);
}

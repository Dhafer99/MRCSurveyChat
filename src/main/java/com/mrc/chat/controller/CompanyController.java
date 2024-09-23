package com.mrc.chat.controller;

import com.mrc.chat.model.Company;
import com.mrc.chat.model.User;
import com.mrc.chat.repository.UserRepository;
import com.mrc.chat.service.CompanyService;
import com.mrc.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final UserRepository userRepository;



    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestParam("name") String name,
                                                 @RequestParam("hrUserId") Long hrUserId,
                                                 @RequestParam(value = "logo", required = false) MultipartFile logo) throws IOException {

        // Retrieve the HR user (the one responsible for this company)
        User hrUser = userRepository.findUserById(hrUserId);

        if (hrUser == null) {
            return ResponseEntity.badRequest().build();
        }

        // Create and set up the company
        Company company = new Company();
        company.setName(name);
        company.setHrUser(hrUser);

        if (logo != null && !logo.isEmpty()) {
            company.setLogo(logo.getBytes());
        }

        Company savedCompany = companyService.saveCompany(company);
        return ResponseEntity.ok(savedCompany);
    }
}

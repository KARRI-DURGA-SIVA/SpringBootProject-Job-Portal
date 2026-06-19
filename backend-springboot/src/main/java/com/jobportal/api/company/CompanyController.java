package com.jobportal.api.company;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyRepository companies;

    public CompanyController(CompanyRepository companies) {
        this.companies = companies;
    }

    @GetMapping
    List<Company> all() {
        return companies.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER','ROLE_ADMIN')")
    Company create(@Valid @RequestBody Company company) {
        return companies.save(company);
    }
}

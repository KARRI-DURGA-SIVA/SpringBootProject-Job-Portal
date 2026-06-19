package com.jobportal.api.company;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "company_name")
    private String companyName;
    private String website;
    private String location;
    @Column(length = 3000)
    private String description;
    @Column(name = "logo_url")
    private String logoUrl;

    protected Company() {
    }

    public Company(String companyName, String website, String location, String description, String logoUrl) {
        this.companyName = companyName;
        this.website = website;
        this.location = location;
        this.description = description;
        this.logoUrl = logoUrl;
    }

    public Long getId() { return id; }
    public String getCompanyName() { return companyName; }
    public String getWebsite() { return website; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getLogoUrl() { return logoUrl; }
}

package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.CompanyService;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> postCreateCompany(@Valid @RequestBody Company reqCompany) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(reqCompany));
    }

    @GetMapping("/companies")
    public ResponseEntity<ResultPaginationDTO> getCompany(
            @RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {
        int current = currentOptional.isPresent() == true ? Integer.parseInt(currentOptional.get())
                : Integer.parseInt(null);
        int pageSize = pageSizeOptional.isPresent() == true ? Integer.parseInt(pageSizeOptional.get())
                : Integer.parseInt(null);

        Pageable pageable = PageRequest.of(current - 1, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.fetchAllCompanies(pageable));
    }

    @PutMapping("/companies")
    public ResponseEntity<Company> putUpdateCompany(@Valid @RequestBody Company reqCompany) {
        return ResponseEntity.ok(this.companyService.handleUpdateCompany(reqCompany));
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") long id) {
        this.companyService.handleDeleteCompany(id);
        return ResponseEntity.ok(null);
    }

}

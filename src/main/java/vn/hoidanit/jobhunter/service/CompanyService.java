package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public ResultPaginationDTO fetchAllCompanies(Pageable pageable) {
        Page<Company> pageCompany = this.companyRepository.findAll(pageable);

        ResultPaginationDTO rsDTO = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setCurrent(pageCompany.getNumber() + 1);
        meta.setPageSize(pageCompany.getSize());

        meta.setPages(pageCompany.getTotalPages());
        meta.setTotal(pageCompany.getTotalElements());

        rsDTO.setMeta(meta);
        rsDTO.setResult(pageCompany.getContent());
        return rsDTO;
    }

    public Company fetchById(long id) {
        Optional<Company> company = this.companyRepository.findById(id);
        if (company.isPresent()) {
            return company.get();
        }
        return null;
    }

    public Company handleUpdateCompany(Company reqCompany) {
        Company company = this.fetchById(reqCompany.getId());
        if (company != null) {
            company.setName(reqCompany.getName());
            company.setDescription(reqCompany.getDescription());
            company.setAddress(reqCompany.getAddress());
            company.setLogo(reqCompany.getLogo());
            company = this.handleCreateCompany(company);
        }
        return company;
    }

    public void handleDeleteCompany(Long id) {
        this.companyRepository.deleteById(id);
    }
}

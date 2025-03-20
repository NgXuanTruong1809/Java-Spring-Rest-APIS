package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.dto.Pagination.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.SkillService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/skills")
    @ApiMessage("skill created")
    public ResponseEntity<Skill> postCreateSkill(@Valid @RequestBody Skill reqSkill) throws IdInvalidException {
        if (this.skillService.fetchSkillByName(reqSkill.getName()) != null) {
            throw new IdInvalidException("Tên skill đã tồn tại");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.handleCreateSkill(reqSkill));
    }

    @PutMapping("/skills")
    @ApiMessage("skill updated")
    public ResponseEntity<Skill> putUpdateSkill(@Valid @RequestBody Skill reqSkill) throws IdInvalidException {
        if (this.skillService.fetchSkillByName(reqSkill.getName()) != null) {
            throw new IdInvalidException("Tên skill đã tồn tại");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.skillService.handleUpdateSkill(reqSkill));
    }

    @GetMapping("/skills")
    public ResponseEntity<ResultPaginationDTO> getAllSkills(
            @Filter Specification<Skill> specification,
            Pageable pageable) {
        return ResponseEntity.ok().body(this.skillService.fetchAllSkills(specification, pageable));
    }

}

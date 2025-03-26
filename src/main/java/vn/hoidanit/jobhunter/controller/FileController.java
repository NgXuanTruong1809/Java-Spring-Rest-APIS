package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1")
public class FileController {
    @PostMapping("/files")
    public String upload() {
        return "upload";
    }

}

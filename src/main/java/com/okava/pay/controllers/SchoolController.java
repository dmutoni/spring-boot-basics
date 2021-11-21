package com.okava.pay.controllers;

import com.okava.pay.services.ISchoolService;
import com.okava.pay.utils.Constants;
import com.okava.pay.utils.Formatter;
import com.okava.pay.utils.Utility;
import com.okava.pay.utils.dtos.RegisterSchoolDTO;
import com.okava.pay.utils.payload.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/schools")
public class SchoolController {

    private final ISchoolService schoolService;

    public SchoolController(ISchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> all(@RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page, @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit) {
        Pageable pageable = Utility.from(page, limit);

        return Formatter.ok(schoolService.all(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> byId(@PathVariable UUID id) {

        return Formatter.ok(schoolService.findById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterSchoolDTO dto) {
        return Formatter.ok("Successfully created the school", schoolService.create(dto));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable UUID id, @Valid @RequestBody RegisterSchoolDTO dto) {
        return Formatter.ok("Successfully updated school",schoolService.update(id, dto));
    }
}

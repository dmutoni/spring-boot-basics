package com.okava.pay.services.impl;

import com.okava.pay.models.School;
import com.okava.pay.repositories.ISchoolRepository;
import com.okava.pay.services.ISchoolService;
import com.okava.pay.utils.dtos.RegisterSchoolDTO;
import com.okava.pay.utils.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;
import java.util.UUID;

public class SchoolServiceImpl implements ISchoolService {
    private final ISchoolRepository schoolRepository;

    public SchoolServiceImpl(ISchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public Page<School> all(Pageable pageable) {
        return schoolRepository.findAll(pageable);
    }

    @Override
    public School findById(UUID id) {
        return schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School", "id", id.toString()));
    }

    @Override
    public Page<School> byName(School school, Pageable pageable) {
        return null;
    }

    @Override
    public School create(RegisterSchoolDTO dto) {
        School school = new School();
        school.setName(dto.getName());
        school.setUserName(dto.getUserName());
        school.setLocation(dto.getLocation());
        school.setHeadMaster(dto.getHeadMaster());

        if (!isUnique(school))
            throw new BadCredentialsException("");
        return null;
    }

    @Override
    public boolean isUnique(School school) {
        Optional<School> schoolOptional = this.schoolRepository.findBy
        return false;
    }
}

package com.okava.pay.services.impl;

import com.okava.pay.models.School;
import com.okava.pay.repositories.ISchoolRepository;
import com.okava.pay.services.ISchoolService;
import com.okava.pay.utils.dtos.RegisterSchoolDTO;
import com.okava.pay.utils.exceptions.BadRequestException;
import com.okava.pay.utils.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SchoolServiceImpl implements ISchoolService {
    private final ISchoolRepository schoolRepository;

    public SchoolServiceImpl(ISchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public School create(RegisterSchoolDTO dto) {
        School school = new School();
        school.setName(dto.getName());
        school.setUserName(dto.getUserName());
        school.setLocation(dto.getLocation());
        school.setHeadMaster(dto.getHeadMaster());

        if (isUnique(school.getUserName()))
            throw new BadRequestException("School name is already taken");
        return schoolRepository.save(school);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public School update(UUID id, RegisterSchoolDTO dto) {
        Optional<School> school = this.schoolRepository.findById(id);

        if (isUnique(dto.getUserName()))
            throw new BadRequestException("School name is already taken");
        if (school.isPresent()) {
            School schoolInDb = school.get();
            schoolInDb.setHeadMaster(dto.getHeadMaster());
            schoolInDb.setName(dto.getName());
            schoolInDb.setLocation(dto.getLocation());
            schoolInDb.setUserName(dto.getUserName());
            return schoolRepository.save(schoolInDb);
        }
        throw new BadRequestException("School name is already taken");
    }

    @Override
    public boolean isUnique(String schoolName) {
        Optional<School> schoolOptional = this.schoolRepository.findByUserName(schoolName);
        return schoolOptional.isPresent();
    }
}

package com.okava.pay.services;

import com.okava.pay.models.School;
import com.okava.pay.utils.dtos.RegisterSchoolDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ISchoolService {

    Page<School> all(Pageable pageable);

    School findById(UUID id);

    Page<School> byName(School school, Pageable pageable);

    School create(RegisterSchoolDTO dto);

    School update(UUID id, RegisterSchoolDTO dto);

    boolean isUnique(String schoolName);

}

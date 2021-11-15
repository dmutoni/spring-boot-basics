package com.okava.pay.repositories;

import com.okava.pay.models.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ISchoolRepository extends JpaRepository<School, UUID> {
    Page<School> findBySchool(School school, Pageable pageable);

}

package com.okava.pay.repositories;

import com.okava.pay.models.User;
import com.okava.pay.models.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {

    Page<User> findByRole(ERole role, Pageable pageable);



    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String email);

    Optional<User> findByNationalId(String email);
}


// null, null
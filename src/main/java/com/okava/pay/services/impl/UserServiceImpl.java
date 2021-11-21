package com.okava.pay.services.impl;

import com.okava.pay.services.MailService;
import com.okava.pay.utils.Utility;
import com.okava.pay.utils.dtos.RegisterDTO;
import com.okava.pay.utils.exceptions.BadRequestException;
import com.okava.pay.utils.exceptions.ResourceNotFoundException;
import com.okava.pay.models.User;
import com.okava.pay.models.enums.ERole;
import com.okava.pay.repositories.IUserRepository;
import com.okava.pay.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final MailService mailService;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @Override
    public Page<User> all(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
    }

    @Override
    public Page<User> byRole(ERole role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }

    @Override
    public User create(RegisterDTO dto) {
        User user = new User();

        user.setRole(ERole.USER);
        user.setEmail(dto.getEmail());
        user.setFullNames(dto.getFullNames());
        user.setPassword(Utility.encode(dto.getPassword()));

        if (!isUnique(user))
            throw new BadRequestException("The provided email is already used in the app");

        user = userRepository.save(user);

        mailService.sendTestEmail(user.getEmail(), "123457");

        return user;
    }

    @Override
    public boolean isUnique(User user) {
        if(user.getEmail() != null)
            if( userRepository.findByEmail(user.getEmail()).isPresent())
                return false;

        if(user.getPhoneNumber() != null)
            if( userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent())
                return false;

        if(user.getNationalId() != null)
            return userRepository.findByPhoneNumber(user.getNationalId()).isEmpty();

        return true;
    }

    @Override
    public User getLoggedInUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser")
            throw new BadRequestException("You are not logged in, try to log in");

        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "email", email));
    }
}

package ru.aisa.test_company.restservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aisa.test_company.restservice.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
}

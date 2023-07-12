package com.ddukddak.backend.service;

import com.ddukddak.backend.domain.User;
import com.ddukddak.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }

    private List<User> findUsers() {
        return userRepository.findAll();
    }

    private User findOne(Long userId) {
        return userRepository.findOne(userId);
    }
}

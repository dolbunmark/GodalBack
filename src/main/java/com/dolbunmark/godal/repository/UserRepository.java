package com.dolbunmark.godal.repository;

import com.dolbunmark.godal.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Cacheable("users")
    Optional<User> findByLogin(String login);
}
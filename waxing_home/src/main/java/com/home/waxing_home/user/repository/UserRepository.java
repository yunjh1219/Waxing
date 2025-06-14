package com.home.waxing_home.user.repository;


import com.home.waxing_home.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNum(String userNum);
    Optional<User> findByNameAndEmail(String name, String email);

    boolean existsByUserNum(String userNum);

}
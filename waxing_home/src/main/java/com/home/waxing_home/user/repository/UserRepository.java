package com.home.waxing_home.user.repository;


import com.home.waxing_home.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserNum(String userNum);

}
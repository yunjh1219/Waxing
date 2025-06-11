package com.home.waxing_home.user.repository;

import com.home.waxing_home.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/* JpaRepository를 상속받으면 User 엔티티와 관련된 기본 CRUD(Create, Read, Update, Delete) 기능을 자동으로 사용할 수 있다.
User는 이 레포지토리가 관리할 엔티티 클래스.
Long은 User 엔티티의 기본 키 타입이 Long이라는 의미다. */

public interface AuthRepository extends JpaRepository<User, Long> {
}

package com.paltus.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paltus.backend.model.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    @Query(value = """
            SELECT ul.loginDate
            FROM UserLogin ul
            WHERE ul.email = :email
            ORDER BY ul.loginDate DESC
                """)
    List<LocalDate> findAllLoginDatesByUserEmailDesc(@Param("email") String email);

    boolean existsByEmailAndLoginDate(String email, LocalDate date);
}

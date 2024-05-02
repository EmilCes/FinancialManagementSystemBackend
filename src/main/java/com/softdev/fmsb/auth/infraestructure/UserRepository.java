package com.softdev.fmsb.auth.infraestructure;

import com.softdev.fmsb.auth.model.User;
import com.softdev.fmsb.worker.infraestructure.UserEssentialsProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByRfc(String rfc);

    Optional<User> findUserByUserNumber(String userNumber);

    Optional<UserEssentialsProjection> findAllBy();
}

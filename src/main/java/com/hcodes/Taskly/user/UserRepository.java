package com.hcodes.Taskly.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(
            @NotBlank(message = "Username is required")
            @Size(max = 50, message = "Username must be at most 50 characters ")
            String username);

   UserEntity findByEmail(@Email @NotBlank(message = "Email is required") String email);
   Boolean existsByUsername(@NotBlank(message = "Username is required") String username);
}

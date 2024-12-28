package com.socialMedia.BuzzHive.userRegistration.repo;


import com.socialMedia.BuzzHive.userRegistration.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Long> {
    Users findByUsername(String username);
   // @Query("SELECT p FROM Users p WHERE p.user_id = :userId")
   // Users findByUserID(String userId);
}

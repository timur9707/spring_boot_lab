package com.epam.springLabEpam.dao;

import com.epam.springLabEpam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UsersDao extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * from User u where u.email = :email", nativeQuery = true)
    User findByEmail(@Param("email") String email);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update User u set u.subscription = :sub where u.id = :id")
    void update(@Param("id") int id, @Param("sub") String subscription);

}

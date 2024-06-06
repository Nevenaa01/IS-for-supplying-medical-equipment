package com.example.ISA2023.back.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    public User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = 2")
    public List<User> getCompanyAdministrators();
    @Query("SELECT u FROM User u ORDER BY u.id DESC LIMIT 1")
    public User getLastUser();
    @Query("SELECT u FROM User u WHERE u.id=?1")
    public User getUserById(Long id);

    Optional<User> findByUsername(String email);
}

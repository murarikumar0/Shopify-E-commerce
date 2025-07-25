package com.ms.Ecomm.repo;

import com.ms.Ecomm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//spring sees and says this
// “Okay, this interface extends JpaRepository, which I understand.
// I will dynamically generate a class at runtime that implements all these methods like findAll(), save(), etc.”
//That dynamically created class is called a proxy object.
@Repository
public interface UserRepository extends JpaRepository<User , Long> {

    // we are telling Spring to create this method on fly.
    User findByEmail(String email);
}

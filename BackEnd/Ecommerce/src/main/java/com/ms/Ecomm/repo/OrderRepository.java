package com.ms.Ecomm.repo;

import com.ms.Ecomm.model.Orders;
import com.ms.Ecomm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {


    // this method will return list of  orders.
    @Query("Select o from Orders o Join FETCH o.user ") // it's a JPA Query , JPA internally convert this Query into MYSql query.
    List<Orders> findAllOrdersWithUsers();

    List<Orders> findByUser(User user);
}

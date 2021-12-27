package com.dolbunmark.godal.repository;

import com.dolbunmark.godal.domain.Order;
import com.dolbunmark.godal.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {

    Optional<Order> findByUserAndActivatedFalse(User user);

    List<Order> findByUserAndActivatedTrue(User user);
}

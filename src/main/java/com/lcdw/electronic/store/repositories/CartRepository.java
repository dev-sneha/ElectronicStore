package com.lcdw.electronic.store.repositories;

import com.lcdw.electronic.store.entities.Cart;
import com.lcdw.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findBYUser(User user); // option is for get if we use get in serviceimp then use option
}

package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;
@Transactional
public interface ICartRepository extends JpaRepository<Cart, Long> {
    @Query(nativeQuery = true,
            value = "SELECT c.cart_id, receiver_address, receiver_email, receiver_name, receiver_phone FROM cart c JOIN customer USING (cart_id) JOIN account a USING(account_id) WHERE a.username = :username")
    Optional<Cart> findCartByUsername(@Param("username") String username);

    @Query(nativeQuery = true,
            value = "SELECT cart_id, receiver_address, receiver_email, receiver_name, receiver_phone FROM cart WHERE cart_id=:id")
    Optional<Cart> findById(@Param("id") Long id);

    @Query(nativeQuery = true,
            value = "SELECT cart_id, receiver_address, receiver_email, receiver_name, receiver_phone FROM cart ORDER BY cart_id DESC LIMIT 1")
    Optional<Cart> findLastCart();

    @Modifying
    @Query(value = "INSERT INTO cart (receiver_name, receiver_address, receiver_phone, receiver_email) values (:name, :address, :phone, :email)",
            nativeQuery = true)
    void insertCart(@Param("name") String name, @Param("address") String address, @Param("phone") String phone, @Param("email") String email);

    @Modifying
    @Query(value = "UPDATE cart SET receiver_name = :name, receiver_address = :address, receiver_phone = :phone, receiver_email = :email WHERE cart_id = :id",
            nativeQuery = true)
    void updateCart(@Param("id") Long id, @Param("name") String name, @Param("address") String address, @Param("phone") String phone, @Param("email") String email);

    @Modifying
    @Query(value = "DELETE FROM cart  WHERE cart_id = :id",
            nativeQuery = true)
    void deleteById(@Param("id") Long id);

}

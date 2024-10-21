package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface ICartDetailRepository extends JpaRepository<CartDetail,Long> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM cart_detail WHERE cart_id = :id AND status = false AND quantity > 0")
    List<CartDetail> findByCartId(@Param("id") Long id);

    @Modifying
    @Query(value = "INSERT INTO cart_detail (product_id, quantity, status, cart_id) values (:product_id, 1, false, :cart_id)",
            nativeQuery = true)
    void insertCart(@Param("product_id") Long product_id, @Param("cart_id") Long cart_id);

    @Modifying
    @Query(value = "UPDATE cart_detail SET product_id = :product_id, quantity = :quantity, status = :status, cart_id = :cart_id WHERE cart_detail_id = :cart_detail_id",
            nativeQuery = true)
    void updateCart(@Param("product_id") Long product_id, @Param("quantity") int quantity, @Param("status") boolean status, @Param("cart_id") Long cart_id, @Param("cart_detail_id") Long cart_detail_id);

    @Modifying
    @Query(value = "DELETE FROM cart_detail  WHERE cart_detail_id = :id",
            nativeQuery = true)
    void deleteById(@Param("id") Long id);

    @Query(nativeQuery = true,
            value = "SELECT * FROM cart_detail WHERE product_id = :product_id AND cart_id = :cart_id AND status = false AND quantity > 0")
    Optional<CartDetail> checkAvailable(@Param("product_id") Long id, @Param("cart_id") Long cart_id);
}

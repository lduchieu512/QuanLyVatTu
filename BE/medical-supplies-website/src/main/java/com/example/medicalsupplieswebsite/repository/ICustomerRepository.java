package com.example.medicalsupplieswebsite.repository;


import com.example.medicalsupplieswebsite.dto.receipt_dto.SupplierDTO;
import com.example.medicalsupplieswebsite.entity.Account;
import com.example.medicalsupplieswebsite.entity.Cart;
import com.example.medicalsupplieswebsite.dto.shipmentdto.CustomerDto;
import com.example.medicalsupplieswebsite.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.medicalsupplieswebsite.entity.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;
import javax.transaction.Transactional;

import java.sql.Date;
import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

@Transactional
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select c.customer_id, c.customer_address ,c.customer_code,c.email, c.customer_img, " +
            "c.date_of_birth, c.gender, c.id_card, c.is_enable," +
            " c.name, c.phone, ct.customer_type_id ," +
            " a.account_id, r.cart_id " +
            "from customer c inner join customer_type ct on c.customer_type_id = ct.customer_type_id left join account a" +
            " on c.account_id = a.account_id left join cart r on c.cart_id = r.cart_id where c.is_enable= true order by c.customer_code ",
            countQuery = "select count(c.customer_id)" +
                    " from customer c" +
                    " inner join customer_type ct on c.customer_type_id = ct.customer_type_id left join account a" +
                    " on c.account_id = a.account_id left join cart r on c.cart_id = r.cart_id where c.is_enable= true order by c.customer_code ",
            nativeQuery = true)
    Page<Customer> findAllCustomers(Pageable pageable);


    @Modifying
    @Query(value = "update customer set is_enable = false where customer_id = :id", nativeQuery = true)
    void deleteCustomerId(@Param("id") Long id);

    /**
     *
     * A0722I1-HieuLD
     */
    @Modifying
    @Query(value = "INSERT INTO customer (`customer_address`,`customer_code`,`customer_img`,`customer_type_id`," +
            "`date_of_birth`,`email`,`gender`,`id_card`,`is_enable`,`name`,`phone`,`cart_id`) VALUES (:customer_address,:customer_code,:customer_img,:customer_type_id,:date_of_birth,:email,:gender,:id_card,:is_enable," +
            ":name,:phone, :cart_id)", nativeQuery = true)
    void insertCustomer(@Param("name") String name,
                        @Param("email") String email,
                        @Param("phone") String phone,
                        @Param("gender") Boolean gender,
                        @Param("date_of_birth") Date date_of_birth,
                        @Param("id_card") String id_card,
                        @Param("customer_address") String address,
                        @Param("customer_img") String img,
                        @Param("customer_type_id") CustomerType customer_type_id,
                        @Param("customer_code") String customer_code,
                        @Param("is_enable") Boolean is_enable,
                        @Param("cart_id") Long cart_id);
    /**
     *
     * A0722I1-HieuLD
     */
    @Query(value = "SELECT * FROM customer order by `customer_code` desc limit 1",nativeQuery = true)
    Customer limitCustomer();

    /**
     *
     * A0722I1-HieuLD
     */
    @Modifying
    @Query(value = "UPDATE customer SET `account_id`=:account_id,`cart_id`=:cart_id,`customer_address`=:customer_address," +
            "`customer_code`=:customer_code,`customer_img`=:customer_img,`customer_type_id`=:customer_type_id," +
            "`date_of_birth`=:date_of_birth,`email`=:email,`gender`=:gender,`id_card`=:id_card,`is_enable`=:is_enable," +
            "`name`=:name,`phone`=:phone WHERE `customer_id`=:customer_id", nativeQuery = true)
    void updateCustomer(@Param("customer_id") Long id,
                        @Param("name") String name,
                        @Param("email") String email,
                        @Param("phone") String phone,
                        @Param("gender") Boolean gender,
                        @Param("date_of_birth") Date date_of_birth,
                        @Param("id_card") String id_card,
                        @Param("customer_address") String address,
                        @Param("customer_img") String img,
                        @Param("customer_type_id") CustomerType customer_type_id,
                        @Param("cart_id") Cart cart,
                        @Param("account_id") Account account,
                        @Param("customer_code") String customer_code,
                        @Param("is_enable") Boolean is_enable);

    /**
     *
     * A0722I1-HieuLD
     */
    @Query("SELECT customer FROM Customer customer WHERE customer.customerId = ?1")
    Customer findAllById(Long id);


    @Query(value = "select c.customer_id, c.name, c.phone, c.gender, c.date_of_birth, c.id_card, c.customer_address, " +
            "c.customer_img, c.is_enable, ct.customer_type_id, cart.cart_id, a.account_id " + "from customer c " +
            "inner join customer_type ct on c.customer_type_id = ct.customer_type_id " +
            "inner join account a on c.account_id = a.account_id " +
            "left join cart on c.cart_id = cart.cart_id " +
            "where (c.is_enable = true) and (a.is_enable = true) and (a.username = :username)",
            nativeQuery = true)
    Optional<Customer> findByUsername(@Param("username") String username);

    /**
     * A0722I1-KhanhNL
     */
    @Query(value = "select c.customer_id, c.customer_code, c.name, c.phone, c.gender, c.date_of_birth, " +
            "c.id_card, c.customer_address, c.customer_img,  ct.customer_type_name, a.username, a.email " +
            "from customer c " +
            "inner join customer_type ct on c.customer_type_id = ct.customer_type_id " +
            "inner join account a on c.account_id = a.account_id " +
            "where (c.is_enable = true) and (a.is_enable = true) and (a.username = :username)",
            nativeQuery = true)
    Optional<Tuple> findUserDetailByUsername(@Param("username") String username);

    /*PhucND dùng phone tìm kiếm khách hàng*/
    /*PhucND*/
    @Query(value = "select customer_id, name,phone, customer_address from customer where phone = ?1", nativeQuery = true)
    Optional<CustomerDto> findByPhoneCustomer(String phone);

    @Query(value = "select customer_address from customer where customer_id = ?1", nativeQuery = true)
    String findAddressByCustomerId(Long customerId);

    @Query(value = "select customer_id,`name`,customer_address  from customer as c join customer_type as ct on c.customer_type_id = ct.customer_type_id where ct.customer_type_id = 2", nativeQuery = true)
    Optional< List<SupplierDTO>> getALlCustomerByCustomerTypeSupplier();

    @Query(value = "select c.customer_id, c.customer_address,c.customer_code,c.email, c.customer_img, c.date_of_birth, c.gender, c.id_card, c.is_enable, c.name, c.phone,ct.customer_type_name,ct.customer_type_id,a.account_id, r.cart_id" +
            " from customer c  join customer_type ct on c.customer_type_id = ct.customer_type_id left join account a " +
            " on c.account_id = a.account_id  left join cart r on c.cart_id = r.cart_id"+
            " where (ct.customer_type_name like concat('%',:type,'%') and c.name like concat('%',:name,'%') and c.customer_address like concat('%',:address,'%') and c.phone like concat('%',:phone,'%')) and (c.is_enable = true) order by c.customer_code "
            , countQuery = "select count(c.customer_id) from customer c join customer_type ct on c.customer_type_id = ct.customer_type_id left join account a on c.account_id = a.account_id  left join cart r on c.cart_id = r.cart_id"+
            " where (ct.customer_type_name like concat('%',:type,'%') and c.name like concat('%',:name,'%') and c.customer_address like concat('%',:address,'%') and c.phone like concat('%',:phone,'%')) and (c.is_enable = true) order by c.customer_code"
            , nativeQuery = true)
    Page<Customer> searchCustomer(@Param("type") String type ,@Param("name") String name ,@Param("address") String address ,@Param("phone") String phone ,Pageable pageable);

    @Query(value = "select customer_id, customer_address, customer_img, date_of_birth, gender, id_card, name, phone, account_id, cart_id, ct.customer_type_id, is_enable, customer_code, email " +
            "from customer c " +
            "inner join customer_type ct on c.customer_type_id = ct.customer_type_id " +
            "where ct.customer_type_id = 2 and c.is_enable = true and c.account_id is not null ",
            nativeQuery = true)
    List<Customer> findAllSuppliers();
}

package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    //select * from customers where cust_id = 'custid'
    Optional<Customer> findByCustomerId(String custId);

    //select * from customers where cust_name like '%custname%'
    List<Customer> findByCustomerNameContains(String custName);
}

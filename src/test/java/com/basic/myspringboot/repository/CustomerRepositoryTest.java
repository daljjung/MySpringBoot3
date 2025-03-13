package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    // ctrl + shift + f10 = 실행 Start
    @Test
    @Rollback(value = false)
    void selectUpdate(){
        Customer customer = new Customer();
        customer.setCustomerId("A001");
        customer.setCustomerName("스프링");
        Customer saveCustomer = repository.save(customer);
        assertThat(saveCustomer).isNotNull();

        Customer customer1 = repository.findByCustomerId("A001")
                .orElseThrow();

        customer1.setCustomerName("스프링뿌뜨");

        
    }


    @Test
    @Rollback(value = false) // false 면 rollback되지 않음
    void insertSelectCustomer(){
        Customer customer = new Customer();
        customer.setCustomerId("A001");
        customer.setCustomerName("스프링");
        //CrudRepository save() 호출 insert
        Customer saveCustomer = repository.save(customer);

        assertThat(saveCustomer).isNotNull();
        assertThat(saveCustomer.getCustomerName()).isEqualTo("스프링");
        assertEquals("A001",saveCustomer.getCustomerId());

        //PK 조회
        //Optional - Null 체크를 위한
        Optional<Customer> optionalById =  repository.findById(1L);
        if(optionalById.isPresent()){
            Customer existCustomer = optionalById.get();
            assertThat(existCustomer.getCustomerName()).isEqualTo("스프링");
        }else{
            System.out.println("customer not found");
        }

        //ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)
        //Consumer : void accept(T), Runnable : void run()
        optionalById.ifPresentOrElse(
                cust -> System.out.println("cust.getCustomerName() " + cust.getCustomerName())
                ,()-> System.out.println("customer not found")
        );

        //T orElseThrow(Supplier<? extends X> exceptionSupplier)  X extends Throwable
        //Supplier : T get()
        Customer aCustomer = repository.findByCustomerId("A001")
                .orElseThrow();
        assertEquals("A001",saveCustomer.getCustomerId());

        //RuntimeException Test
        /*Customer aCustomer = repository.findByCustomerId("B001")
                .orElseThrow(() -> new RuntimeException("Customer Not Found!!"));*/



    }

}
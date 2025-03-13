package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "customers")
@Getter
@Setter
@DynamicUpdate
public class Customer {
    @Id //PK
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, name = "cust_id")
    private String customerId;
    @Column(nullable = false, name="cust_name") //해도되고 안해도되는 컬럼명 따로 하고싶을떄
    private String customerName;

}

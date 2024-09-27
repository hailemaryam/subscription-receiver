package com.hmmk.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;

@Entity
@Table(name = "PhoneList")
@Cacheable
public class PhoneList extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    public String serviceId;
    public String productId;
    public String customerSegmentGroup;
    public String phone;
    public Boolean status;
    public Instant registrationTime;
    public Instant updateTime;
}

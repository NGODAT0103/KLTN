package com.example.yamp.usersvc.persistence.entity;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"customerUuid", "name"})})
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID uuid;

  @Column(nullable = false)
  private UUID customerUuid;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String cityName;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private String province;

  @Column(nullable = false)
  private String street;

  @Column(nullable = false)
  private String ward;

  @Column(nullable = false)
  private String district;

  @Column(nullable = false)
  private String addressType;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerUuid", insertable = false, updatable = false)
  private Customer customer;
}

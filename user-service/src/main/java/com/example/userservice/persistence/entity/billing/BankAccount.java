package com.example.userservice.persistence.entity.billing;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;



@Entity
@Getter
@Setter
public class BankAccount extends BillingDetail{
    private String account;
    private String bankName;
    private String swift;
}
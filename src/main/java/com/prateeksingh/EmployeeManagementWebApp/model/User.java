package com.prateeksingh.EmployeeManagementWebApp.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name =  "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

   @Id
   @GeneratedValue(
         strategy = GenerationType.SEQUENCE,
         generator = "user_seq"
   )
   @SequenceGenerator(
         name = "user_seq",
         allocationSize = 1,
         initialValue = 2
   )
   private long id;

   @Column(name = "first_name")
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "email")
   private String email;

   @Column(name = "password")
   private String password;
}

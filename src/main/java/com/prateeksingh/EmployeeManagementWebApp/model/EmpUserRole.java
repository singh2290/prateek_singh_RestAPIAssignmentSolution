package com.prateeksingh.EmployeeManagementWebApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name =  "emp_user_role")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmpUserRole {
   @Id
   @GeneratedValue(
         strategy = GenerationType.SEQUENCE,
         generator = "emp_user_role_seq"
   )
   @SequenceGenerator(
         name = "emp_user_role_seq",
         allocationSize = 1,
         initialValue = 2
   )
   private long id;

   @Column(name = "emp_user_id")
   private long empUserId;

   @Column(name = "emp_role_id")
   private long empRoleId;

}

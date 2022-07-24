package com.prateeksingh.EmployeeManagementWebApp.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable, Cloneable{

   private static final long serialVersionUID = -869410492418696747L;

   @Id
   @GeneratedValue(
         strategy = GenerationType.SEQUENCE,
         generator = "seq_post"
   )
   @SequenceGenerator(
         name = "seq_post",
         allocationSize = 1,
         initialValue = 3
   )
   private long id;

   @Column(name = "name")
   private String name;

}

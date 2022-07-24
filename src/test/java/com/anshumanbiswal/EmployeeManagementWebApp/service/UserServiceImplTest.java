package com.anshumanbiswal.EmployeeManagementWebApp.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserServiceImplTest {

   @Test
   void save() {
      String pwd =  new BCryptPasswordEncoder().encode("apassword");
      assertNotNull(pwd);
   }
}

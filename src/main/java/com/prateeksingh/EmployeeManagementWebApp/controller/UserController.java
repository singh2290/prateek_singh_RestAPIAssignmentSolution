package com.prateeksingh.EmployeeManagementWebApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prateeksingh.EmployeeManagementWebApp.dto.SearchResultDto;
import com.prateeksingh.EmployeeManagementWebApp.dto.UserRegistrationDto;
import com.prateeksingh.EmployeeManagementWebApp.model.Role;
import com.prateeksingh.EmployeeManagementWebApp.model.User;
import com.prateeksingh.EmployeeManagementWebApp.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

   private IUserService userService;

   public UserController(IUserService userService) {
      super();
      this.userService = userService;
   }

   @ModelAttribute("userRegistrationDto")
   public UserRegistrationDto userRegistrationDto() {
      return new UserRegistrationDto();
   }

   @GetMapping("/showNewUserForm")
   public String showNewUserForm(Model model) {
      // create model attribute to bind form data
      UserRegistrationDto user = new UserRegistrationDto();
      model.addAttribute("userRegistrationDto", user);
      return "newUser";
   }





   @RequestMapping("/search")
   public String viewHomePage(Model model, @Param("keyword") String keyword) {
      List<User> userList = new ArrayList<>();
      if(NumberUtils.isParsable(keyword)) {
         userList = userService.searchUser(Long.parseLong(keyword), null);
      }
      model.addAttribute("userList", userList);
      model.addAttribute("keyword", keyword);

      return "searchUser";
   }

   @PostMapping("/add")
   public String addUser(@ModelAttribute("userRegistrationDto") UserRegistrationDto registrationDto) {
      userService.save(registrationDto);
      return "redirect:/user/list";
   }

   @GetMapping("/list")
   public String userList(Model model) {
      return findPaginatedRole(1, "firstName", "asc", model);
   }

   @GetMapping("/page/{pageNo}")
   public String findPaginatedRole(@PathVariable(value = "pageNo") int pageNo,
         @RequestParam("sortField") String sortField,
         @RequestParam("sortDir") String sortDir,
         Model model) {
      int pageSize = 5;

      Page<User> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir);
      List<User> userList = page.getContent();

      model.addAttribute("currentPage", pageNo);
      model.addAttribute("totalPages", page.getTotalPages());
      model.addAttribute("totalItems", page.getTotalElements());

      model.addAttribute("sortField", sortField);
      model.addAttribute("sortDir", sortDir);
      model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

      model.addAttribute("userList", userList);
      return "userList";
   }
}

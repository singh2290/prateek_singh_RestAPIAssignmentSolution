package com.prateeksingh.EmployeeManagementWebApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anshumanbiswal.EmployeeManagementWebApp.dto.RoleDto;
import com.anshumanbiswal.EmployeeManagementWebApp.dto.UserRegistrationDto;
import com.anshumanbiswal.EmployeeManagementWebApp.model.Employee;
import com.anshumanbiswal.EmployeeManagementWebApp.model.Role;
import com.anshumanbiswal.EmployeeManagementWebApp.repository.RoleRepository;
import com.anshumanbiswal.EmployeeManagementWebApp.service.IRoleService;

@Controller
public class RoleController {

   @Autowired
   private IRoleService roleService;

   @PostMapping("/role/add")
   public String addRole(@ModelAttribute("role") Role role) {
      roleService.addRole(role);
      return "redirect:/role/list";
   }

   @GetMapping("/role/showNewRoleForm")
   public String showNewRoleForm(Model model) {
      // create model attribute to bind form data
      Role role = new Role();
      model.addAttribute("role", role);
      return "new_user_role";
   }

   @GetMapping("/role/list")
   public String roleList(Model model) {
      return findPaginatedRole(1, "name", "asc", model);
   }

   @GetMapping("/role/page/{pageNo}")
   public String findPaginatedRole(@PathVariable (value = "pageNo") int pageNo,
         @RequestParam("sortField") String sortField,
         @RequestParam("sortDir") String sortDir,
         Model model) {
      int pageSize = 5;

      Page<Role> page = roleService.findPaginated(pageNo, pageSize, sortField, sortDir);
      List<Role> roleList = page.getContent();

      model.addAttribute("currentPage", pageNo);
      model.addAttribute("totalPages", page.getTotalPages());
      model.addAttribute("totalItems", page.getTotalElements());

      model.addAttribute("sortField", sortField);
      model.addAttribute("sortDir", sortDir);
      model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

      model.addAttribute("roleList", roleList);
      return "role";
   }
}

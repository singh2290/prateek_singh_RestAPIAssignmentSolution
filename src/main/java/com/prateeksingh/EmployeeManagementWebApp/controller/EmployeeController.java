package com.prateeksingh.EmployeeManagementWebApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.prateeksingh.EmployeeManagementWebApp.dto.SearchResultDto;
import com.prateeksingh.EmployeeManagementWebApp.model.Employee;
import com.prateeksingh.EmployeeManagementWebApp.model.User;
import com.prateeksingh.EmployeeManagementWebApp.service.IEmployeeService;

@Controller
public class EmployeeController {
   @Autowired
   private IEmployeeService employeeService;

   // display list of employees
   @GetMapping("/")
   public String viewHomePage(Model model) {
      return findPaginated(1, "firstName", "asc", model);
   }

   @GetMapping("/showNewEmployeeForm")
   public String showNewEmployeeForm(Model model) {
      // create model attribute to bind form data
      Employee employee = new Employee();
      model.addAttribute("employee", employee);
      return "new_employee";
   }

   @PostMapping("/saveEmployee")
   @PreAuthorize("hasRole('ADMIN')")
   public String saveEmployee(@ModelAttribute("employee") Employee employee) {
      // save employee to database
      employeeService.saveEmployee(employee);
      return "redirect:/";
   }

   @GetMapping("/showFormForUpdate/{id}")
   public String showFormForUpdate(@PathVariable( value = "id") long id, Model model) {

      // get employee from the service
      Employee employee = employeeService.getEmployeeById(id);

      // set employee as a model attribute to pre-populate the form
      model.addAttribute("employee", employee);
      return "update_employee";
   }

   @GetMapping("/deleteEmployee/{id}")
   public String deleteEmployee(@PathVariable (value = "id") long id) {

      // call delete employee method
      this.employeeService.deleteEmployeeById(id);
      return "redirect:/";
   }


   @GetMapping("/page/{pageNo}")
   public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
         @RequestParam("sortField") String sortField,
         @RequestParam("sortDir") String sortDir,
         Model model) {
      int pageSize = 5;

      Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
      List<Employee> listEmployees = page.getContent();

      model.addAttribute("currentPage", pageNo);
      model.addAttribute("totalPages", page.getTotalPages());
      model.addAttribute("totalItems", page.getTotalElements());

      model.addAttribute("sortField", sortField);
      model.addAttribute("sortDir", sortDir);
      model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

      model.addAttribute("listEmployees", listEmployees);
      return "index";
   }

   @RequestMapping("/search")
   public String searchUserByfname(Model model, @Param("keyword") String keyword) {
      List<Employee> employeeList = new ArrayList<>();
      if(NumberUtils.isParsable(keyword)) {
         employeeList = employeeService.searchEmployee(Long.parseLong(keyword), null);
      } else {
         employeeList = employeeService.searchEmployee(null, keyword);
      }
      model.addAttribute("employeeList", employeeList);
      model.addAttribute("keyword", keyword);

      return "searchUser";
   }

   @GetMapping("/showNewSearchForm")
   public String showNewSearchForm(Model model) {
      // create model attribute to bind form data
      SearchResultDto searchResultDto = new SearchResultDto();
      model.addAttribute("searchResultDto", searchResultDto);
      return "searchUser";
   }
}

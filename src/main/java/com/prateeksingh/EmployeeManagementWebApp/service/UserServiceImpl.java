package com.prateeksingh.EmployeeManagementWebApp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.prateeksingh.EmployeeManagementWebApp.dto.UserRegistrationDto;
import com.prateeksingh.EmployeeManagementWebApp.model.Role;
import com.prateeksingh.EmployeeManagementWebApp.model.User;
import com.prateeksingh.EmployeeManagementWebApp.model.EmpUserRole;
import com.prateeksingh.EmployeeManagementWebApp.repository.EmpUserRoleRepository;
import com.prateeksingh.EmployeeManagementWebApp.repository.RoleRepository;
import com.prateeksingh.EmployeeManagementWebApp.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

   private UserRepository userRepository;
   private EmpUserRoleRepository empUserRoleRepository;
   private RoleRepository roleRepository;

   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

   @Autowired
   public UserServiceImpl(UserRepository userRepository, EmpUserRoleRepository empUserRoleRepository,
         RoleRepository  roleRepository) {
      super();
      this.userRepository = userRepository;
      this.empUserRoleRepository = empUserRoleRepository;
      this.roleRepository = roleRepository;
   }

   @Override
   public User save(UserRegistrationDto registrationDto) {
      User user = new User();
      user.setFirstName(registrationDto.getFirstName());
      user.setLastName(registrationDto.getLastName());
      user.setEmail(registrationDto.getEmail());
      user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
      User savedUser =  userRepository.save(user);

      List<Role> roles = new ArrayList<>();
      roles.add(roleRepository.findByName("ROLE_USER"));
      String csvrolesString = registrationDto.getRoles();
      if (!ObjectUtils.isEmpty(csvrolesString)) {
         String[] rolesArray = csvrolesString.split(",");
         for (String roleStr: rolesArray) {
            try {
               Role role = roleRepository.findByName(roleStr);
               if (role != null  && !roles.contains(role)) {
                  roles.add(role);
               }
            }catch (Exception ex) {
            }
         }
      }
      for (Role role : roles) {
         EmpUserRole empUserRole = new EmpUserRole();
         empUserRole.setEmpUserId(savedUser.getId());
         empUserRole.setEmpRoleId(role.getId());
         empUserRoleRepository.save(empUserRole);
      }

      return savedUser;
   }

   @Override
   public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
      Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();

      Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
      return this.userRepository.findAll(pageable);
   }

   @Override
   public  List<User> searchUser(Long id, String keyWord) {
      List<User> users = new ArrayList<>();
      if (id != null) {
         Optional<User> optionalUser =  this.userRepository.findById(id);
         User user = optionalUser.orElseGet(User::new);
         users.add(user);
      }
      if (!ObjectUtils.isEmpty(keyWord)) {
         List<User> list = this.userRepository.findAllByFirstName(keyWord);
         if (!CollectionUtils.isEmpty(list)) {
            users.addAll(list);
         }
      }
      return users;
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      User user = userRepository.findByEmail(username);
      if(user == null) {
         throw new UsernameNotFoundException("Invalid username or password.");
      }
      Collection<EmpUserRole> empUserRoleList = empUserRoleRepository.findAllByEmpUserId(user.getId());
      List<Long> roleIdList = empUserRoleList.stream().map(EmpUserRole::getEmpRoleId).collect(Collectors.toList());
      List<Role> roles = roleRepository.findByIdIn(roleIdList);
      return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(
            roles));
   }

   private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
      return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
   }
}

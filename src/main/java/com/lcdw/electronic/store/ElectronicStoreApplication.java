package com.lcdw.electronic.store;

import com.lcdw.electronic.store.entities.Role;
import com.lcdw.electronic.store.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ElectronicStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

	@Value("${normal.role.id}")
	private String role_normal_id;

	@Value("${admin.role.id}")
	private String role_admin_id;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception {

   System.out.println(passwordEncoder.encode("abcd"));

   try{

	   Role role_admin= Role.builder().roleId(role_admin_id).roleName("ROLE_ADMIN").build();
	   Role role_normal= Role.builder().roleId(role_normal_id).roleName("ROLE_NORMAL").build();
	   roleRepository.save(role_admin);
	   roleRepository.save(role_normal);

   }catch(Exception e) {
	   e.printStackTrace();
   }
   }


	}


package com.example.techh.repositories;

import com.example.techh.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, String>{
}

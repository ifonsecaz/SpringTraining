package com.logprof.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logprof.users.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,Long>{
}
package com.ten022.diary.repository;

import com.ten022.diary.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

   Optional<Member> findByEmail(String email);

}
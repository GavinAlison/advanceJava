package com.alison.demo1.repository;

import com.alison.demo1.domain.po.Hello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface HelloRepository extends JpaRepository<Hello, Long>, JpaSpecificationExecutor<Hello> {
}

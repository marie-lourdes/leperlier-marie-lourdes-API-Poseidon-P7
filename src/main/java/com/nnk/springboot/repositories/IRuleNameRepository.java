package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRuleNameRepository extends JpaRepository<RuleName, Integer> {
}

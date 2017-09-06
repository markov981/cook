package com.libertymutual.goforcode.cook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.libertymutual.goforcode.cook.models.Instruction;

@Repository
public interface InstrRepository extends JpaRepository<Instruction, Long>{

}

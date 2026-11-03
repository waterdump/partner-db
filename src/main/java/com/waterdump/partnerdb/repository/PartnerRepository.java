package com.waterdump.partnerdb.repository;

import com.waterdump.partnerdb.domain.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    List<Partner> findByNameContaining(String keyword);
}

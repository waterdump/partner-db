package com.waterdump.partnerdb.repository;

import com.waterdump.partnerdb.domain.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    List<Partner> findByNameContaining(String keyword);

    // 구분이 categories 중 하나이고, 마지막 연락일이 date보다 이전인 업체 (오래된 순)
    List<Partner> findByCategoryInAndLastContactDateBeforeOrderByLastContactDateAsc(
            List<String> categories, LocalDate date);
}

package com.waterdump.partnerdb.service;

import com.waterdump.partnerdb.domain.Partner;
import com.waterdump.partnerdb.repository.PartnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }
}

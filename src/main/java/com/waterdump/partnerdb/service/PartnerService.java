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

    // 업체 등록
    public Long register(Partner partner) {
        partner.setCreatedDate(LocalDate.now());      // 입력일 = 오늘
        if (partner.getSalesStatus() == null) {
            partner.setSalesStatus("미접촉");          // 영업상태 기본값
        }
        partnerRepository.save(partner);
        return partner.getId();
    }

    // 전체 업체 목록
    public List<Partner> findPartners() {
        return partnerRepository.findAll();
    }

    // 번호로 업체 하나 찾기
    public Optional<Partner> findOne(Long id) {
        return partnerRepository.findById(id);
    }

    // 업체명으로 검색
    public List<Partner> search(String keyword) {
        return partnerRepository.findByNameContaining(keyword);
    }

    // 업체 정보 수정
    public void update(Long id, Partner form) {
        Partner partner = partnerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("업체가 없습니다. id=" + id));

        partner.setName(form.getName());
        partner.setEmail(form.getEmail());
        partner.setPhone(form.getPhone());
        partner.setMobile(form.getMobile());
        partner.setAddress(form.getAddress());
        partner.setBusinessName(form.getBusinessName());
        partner.setManager(form.getManager());
        partner.setCeo(form.getCeo());
        partner.setHomepage(form.getHomepage());
        partner.setCategory(form.getCategory());
        partner.setMemo(form.getMemo());
    }

    public void delete(Long id) {
        partnerRepository.deleteById(id);
    }
}
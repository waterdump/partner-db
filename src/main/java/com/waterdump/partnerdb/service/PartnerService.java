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
        partner.setName(NameNormalizer.tidy(partner.getName()));
        validateDuplicate(partner);                    // 중복 검증 (걸리면 여기서 멈춤)
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

        partner.setName(NameNormalizer.tidy(form.getName()));
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

    // 업체 삭제
    public void delete(Long id) {
        partnerRepository.deleteById(id);
    }

    // 중복 검증 (엑셀 매크로 규칙을 웹에 맞게 정리)
    private void validateDuplicate(Partner newPartner) {
        String nameKey = NameNormalizer.forCompare(newPartner.getName());
        String email = newPartner.getEmail() == null ? "" : newPartner.getEmail().trim();

        for (Partner existing : partnerRepository.findAll()) {
            boolean sameName = NameNormalizer.forCompare(existing.getName()).equals(nameKey);
            String category = existing.getCategory() == null ? "" : existing.getCategory();

            // 규칙 1: 구독취소·제외 업체와 업체명이 같으면 차단
            if (sameName && (category.equals("구독취소") || category.equals("제외"))) {
                throw new IllegalStateException(category + " 업체라 등록할 수 없습니다: " + existing.getName());
            }

            // 규칙 2: 이메일이 같은 업체가 있으면 차단
            if (!email.isEmpty() && email.equalsIgnoreCase(existing.getEmail())) {
                throw new IllegalStateException("이미 있는 업체입니다 (이메일 일치): " + existing.getName());
            }

            // 규칙 3: 이메일이 없는데 업체명이 같으면 차단
            if (email.isEmpty() && sameName) {
                throw new IllegalStateException("이미 있는 업체입니다 (업체명 일치): " + existing.getName());
            }
        }
        // 규칙 4: 여기까지 왔으면 등록 가능
    }
}
package com.waterdump.partnerdb.controller;

import com.waterdump.partnerdb.domain.Partner;

import java.time.LocalDate;

// API로 내보낼 칸만 담은 응답 전용 상자 (휴대전화·주소·대표자·비고는 내보내지 않음)
public record PartnerResponse(
        Long id,
        String name,
        String email,
        String phone,
        String manager,
        String category,
        String salesStatus,
        LocalDate createdDate,
        LocalDate lastContactDate
) {
    public static PartnerResponse from(Partner p) {
        return new PartnerResponse(
                p.getId(),
                p.getName(),
                p.getEmail(),
                p.getPhone(),
                p.getManager(),
                p.getCategory(),
                p.getSalesStatus(),
                p.getCreatedDate(),
                p.getLastContactDate()
        );
    }
}

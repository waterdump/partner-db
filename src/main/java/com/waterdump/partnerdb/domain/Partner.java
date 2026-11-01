package com.waterdump.partnerdb.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;          // 업체명
    private String email;         // 이메일
    private String phone;         // 전화번호
    private String mobile;        // 휴대전화
    private String address;       // 주소
    private String businessName;  // 사업명
    private String manager;       // 담당자
    private String ceo;           // 대표자
    private String homepage;      // 홈페이지
    private String category;      // 구분 (진행/문의/제외/구독취소)
    private String memo;          // 비고
    private String salesStatus;   // 영업상태
    private LocalDate createdDate;      // 입력일
    private LocalDate lastContactDate;  // 마지막 연락일

}
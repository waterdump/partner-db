package com.waterdump.partnerdb.controller;

import com.waterdump.partnerdb.domain.Partner;
import com.waterdump.partnerdb.service.PartnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/partners")
public class PartnerApiController {

    private final PartnerService partnerService;

    public PartnerApiController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    // 업체 목록 (검색어가 있으면 검색 결과)
    @GetMapping
    public List<PartnerResponse> list(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Partner> partners;
        if (keyword == null || keyword.isBlank()) {
            partners = partnerService.findPartners();
        } else {
            partners = partnerService.search(keyword);
        }
        return partners.stream().map(PartnerResponse::from).toList();
    }

    // 미접촉 업체 목록
    @GetMapping("/inactive")
    public List<PartnerResponse> inactive(@RequestParam(value = "days", defaultValue = "90") int days) {
        return partnerService.findInactive(days).stream().map(PartnerResponse::from).toList();
    }

    // 업체 하나 (없으면 404)
    @GetMapping("/{id}")
    public ResponseEntity<PartnerResponse> one(@PathVariable("id") Long id) {
        return partnerService.findOne(id)
                .map(PartnerResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

package com.waterdump.partnerdb.controller;

import org.springframework.web.bind.annotation.PathVariable;
import com.waterdump.partnerdb.domain.Partner;
import com.waterdump.partnerdb.service.PartnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    // 첫 화면 → 업체 목록으로 이동
    @GetMapping("/")
    public String home() {
        return "redirect:/partners";
    }

    // 업체 목록 (검색어가 있으면 검색 결과)
    @GetMapping("/partners")
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Partner> partners;
        if (keyword == null || keyword.isBlank()) {
            partners = partnerService.findPartners();
        } else {
            partners = partnerService.search(keyword);
        }
        model.addAttribute("partners", partners);
        model.addAttribute("keyword", keyword);
        return "partners/partnerList";
    }

    // 업체 등록 화면
    @GetMapping("/partners/new")
    public String createForm(Model model) {
        model.addAttribute("partner", new Partner());
        return "partners/createPartnerForm";
    }

    // 업체 등록 처리
    @PostMapping("/partners/new")
    public String create(@ModelAttribute Partner partner) {
        partnerService.register(partner);
        return "redirect:/partners";
    }

    //    업체 수정 화면
    @GetMapping("/partners/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Partner partner = partnerService.findOne(id)
                .orElseThrow(() -> new IllegalArgumentException("업체가 없습니다. id=" + id));
        model.addAttribute("partner", partner);
        return "partners/editPartnerForm";
    }

    // 업체 수정 처리
    @PostMapping("/partners/{id}/edit")
    public String edit(@PathVariable("id") Long id, @ModelAttribute Partner form) {
        partnerService.update(id, form);
        return "redirect:/partners";
    }
}
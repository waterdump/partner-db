package com.waterdump.partnerdb;

import com.waterdump.partnerdb.domain.Partner;
import com.waterdump.partnerdb.service.PartnerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final PartnerService partnerService;

    public SampleDataLoader(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @Override
    public void run(String... args) {
        // 이미 업체가 있으면 넣지 않음 (실행할 때마다 중복으로 들어가는 것 방지)
        if (!partnerService.findPartners().isEmpty()) {
            return;
        }

        LocalDate today = LocalDate.now();

        add("(주)한빛상사", "info@hanbit.example.com", "02-0000-1001", "이수진", "진행", today.minusDays(10));
        add("푸른식품(주)", "contact@pureun.example.com", "031-000-1002", "김민호", "문의", today.minusDays(120));
        add("(유)새봄테크", "", "051-000-1003", "정다은", "진행", today.minusDays(200));
        add("다온물산", "sales@daon.example.com", "02-0000-1004", "", "", null);
        add("(주)미래로직스", "hello@mirae.example.com", "032-000-1005", "최우진", "문의", today.minusDays(400));
        add("한결인쇄", "print@hangyeol.example.com", "02-0000-1006", "오세라", "제외", today.minusDays(30));
        add("(주)솔빛화학", "sol@solbit.example.com", "043-000-1007", "", "구독취소", null);
        add("가람디자인", "garam@garam.example.com", "02-0000-1008", "윤가람", "진행", today.minusDays(3));
        add("(주)누리에너지", "", "061-000-1009", "", "", null);
        add("별하늘제과", "star@byeol.example.com", "053-000-1010", "한지우", "문의", today.minusDays(95));
    }

    private void add(String name, String email, String phone, String manager,
                     String category, LocalDate lastContactDate) {
        Partner partner = new Partner();
        partner.setName(name);
        partner.setEmail(email);
        partner.setPhone(phone);
        partner.setManager(manager);
        partner.setCategory(category);
        partner.setLastContactDate(lastContactDate);
        partnerService.register(partner);
    }
}

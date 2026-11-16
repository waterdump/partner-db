package com.waterdump.partnerdb.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NameNormalizerTest {

    @Test
    void 비교용_이름은_회사표기와_공백을_무시한다() {
        assertThat(NameNormalizer.forCompare("(주)한빛상사")).isEqualTo("한빛상사");
        assertThat(NameNormalizer.forCompare("한빛상사(주)")).isEqualTo("한빛상사");
        assertThat(NameNormalizer.forCompare("주식회사 한빛 상사")).isEqualTo("한빛상사");
        assertThat(NameNormalizer.forCompare("㈜한빛상사")).isEqualTo("한빛상사");
    }

    @Test
    void 저장용_이름은_주식회사를_주로_붙여쓴다() {
        assertThat(NameNormalizer.tidy("  주식회사 한빛상사 ")).isEqualTo("(주)한빛상사");
        assertThat(NameNormalizer.tidy("한빛상사 주식회사")).isEqualTo("한빛상사(주)");
        assertThat(NameNormalizer.tidy("㈜한빛상사")).isEqualTo("(주)한빛상사");
        assertThat(NameNormalizer.tidy("유한회사 새봄테크")).isEqualTo("(유)새봄테크");
        assertThat(NameNormalizer.tidy("(주) 한빛상사")).isEqualTo("(주)한빛상사");
        assertThat(NameNormalizer.tidy("다온물산")).isEqualTo("다온물산");
    }
}
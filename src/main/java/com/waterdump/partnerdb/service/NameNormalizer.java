package com.waterdump.partnerdb.service;

public class NameNormalizer {

    private static final String[] COMPANY_MARKS = {
            "(주)", "(유)", "(합)", "(사)", "㈜", "주식회사", "유한회사", "합자회사"
    };

    // 비교용: 회사 표기와 공백을 모두 지운 이름 (DB에 저장하지 않음)
    public static String forCompare(String name) {
        if (name == null) {
            return "";
        }
        String result = name;
        for (String mark : COMPANY_MARKS) {
            result = result.replace(mark, "");
        }
        return result.replaceAll("\\s", "");
    }

    // 저장용: 앞뒤 공백 정리 + 주식회사/유한회사를 (주)/(유)로 붙여쓰기
    public static String tidy(String name) {
        if (name == null) {
            return null;
        }
        String result = name.trim();
        result = result.replace("㈜", "(주)");
        result = result.replaceAll("^주식회사\\s*", "(주)");    // 맨 앞 주식회사
        result = result.replaceAll("\\s*주식회사$", "(주)");    // 맨 뒤 주식회사
        result = result.replaceAll("^유한회사\\s*", "(유)");
        result = result.replaceAll("\\s*유한회사$", "(유)");
        result = result.replaceAll("\\(주\\)\\s+", "(주)");     // "(주) 한빛" → "(주)한빛"
        result = result.replaceAll("\\s+\\(주\\)", "(주)");     // "한빛 (주)" → "한빛(주)"
        return result;
    }
}
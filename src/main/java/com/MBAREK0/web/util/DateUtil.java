package com.MBAREK0.web.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {

    public static List<String> getDisabledDates() {
        List<String> dates = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (int i = 0; i <= today.getDayOfYear(); i++) {
            dates.add(today.minusDays(i).toString());
        }

        dates.add(today.plusDays(1).toString());
        dates.add(today.plusDays(2).toString());
        dates.add(today.plusDays(3).toString());

        return dates;
    }
    public static List<String> getDisabledDates(LocalDate specifiedDate) {
        List<String> dates = new ArrayList<>();

        for (int i = 0; i <= specifiedDate.getDayOfYear(); i++) {
            dates.add(specifiedDate.minusDays(i).toString());
        }

        dates.add(specifiedDate.plusDays(1).toString());

        return dates;
    }}

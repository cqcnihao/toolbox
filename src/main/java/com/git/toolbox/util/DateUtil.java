package com.git.toolbox.util;

import java.time.LocalDate;
import java.time.Period;

/**
 * Created by poan on 2017/09/12.
 */
public class DateUtil {

    public static Period gaps(LocalDate ago, LocalDate now) {
        return Period.between(ago, now);
    }

    public static void main(String[] args) {
        Period gaps = gaps(LocalDate.of(1992, 9, 9),LocalDate.now());
        int years = gaps.getYears();
        System.out.println(years);
    }
}

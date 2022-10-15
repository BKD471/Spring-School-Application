package com.example.school.controller;

import com.example.school.model.HoliDay;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HolidaysController {

    @GetMapping("/holidays")
    public String displayHolidays(Model model) {
        List<HoliDay> holidays = Arrays.asList(
                new HoliDay(" Jan 1 ","New Year's Day", HoliDay.Type.FESTIVAL),
                new HoliDay(" Oct 31 ","Halloween", HoliDay.Type.FESTIVAL),
                new HoliDay(" Nov 24 ","Thanksgiving Day", HoliDay.Type.FESTIVAL),
                new HoliDay(" Dec 25 ","Christmas", HoliDay.Type.FESTIVAL),
                new HoliDay(" Jan 17 ","Martin Luther King Jr. Day", HoliDay.Type.FEDERAL),
                new HoliDay(" July 4 ","Independence Day", HoliDay.Type.FEDERAL),
                new HoliDay(" Sep 5 ","Labor Day", HoliDay.Type.FEDERAL),
                new HoliDay(" Nov 11 ","Veterans Day", HoliDay.Type.FEDERAL)
        );
        HoliDay.Type[] types = HoliDay.Type.values();
        for (HoliDay.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}

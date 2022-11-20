package com.example.school.controller;

import com.example.school.model.HoliDay;
import com.example.school.repository.HolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HolidaysController {

    @Autowired
    HolidaysRepository holidaysRepository;
    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable String display, Model model) {
        if (display != null && display.equalsIgnoreCase("federal")) {
            model.addAttribute("federal", true);
        } else if (display != null && display.equalsIgnoreCase("festival")) {
            model.addAttribute("festival", true);
        } else {
            model.addAttribute("federal", true);
            model.addAttribute("festival", true);
        }
        List<HoliDay> holidays = holidaysRepository.findAllHolidays();
        HoliDay.Type[] types = HoliDay.Type.values();
        for (HoliDay.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}

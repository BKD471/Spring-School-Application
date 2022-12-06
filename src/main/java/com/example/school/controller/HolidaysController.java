package com.example.school.controller;

import com.example.school.model.HoliDay;
import com.example.school.repository.HolidaysRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HolidaysController {

    @Autowired
    HolidaysRepositoryJPA holidaysRepositoryJpa;
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
        Iterable<HoliDay> holidays = holidaysRepositoryJpa.findAll();
        List<HoliDay> holidayList= StreamSupport.stream(holidays.spliterator(),false).collect(Collectors.toList());
        HoliDay.Type[] types = HoliDay.Type.values();
        for (HoliDay.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidayList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}

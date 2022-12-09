package com.example.school.controller;

import com.example.school.model.Courses;
import com.example.school.model.Person;
import com.example.school.model.PhoenixClass;
import com.example.school.repository.CoursesRepository;
import com.example.school.repository.PersonRepository;
import com.example.school.repository.PhoenixClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PhoenixClassRepository phoenixClassRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model) {
        List<PhoenixClass> phoenixClasses = phoenixClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("eazyClasses", phoenixClasses);
        modelAndView.addObject("eazyClass", new PhoenixClass());
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model, @ModelAttribute("eazyClass") PhoenixClass phoenixClass) {
        phoenixClassRepository.save(phoenixClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam int id) {
        Optional<PhoenixClass> phoenixClass = phoenixClassRepository.findById(id);
        for (Person person : phoenixClass.get().getPersons()) {
            person.setPhoenixClass(null);
            personRepository.save(person);
        }
        phoenixClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<PhoenixClass> phoenixClasses = phoenixClassRepository.findById(classId);
        session.setAttribute("loggedInClass", phoenixClasses.get());
        modelAndView.addObject("eazyClass", phoenixClasses.get());
        modelAndView.addObject("person", new Person());
        if (error != null) {
            errorMessage = "Invalid Email Entered";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        Person personEntity = personRepository.readByEmail(person.getEmail());
        PhoenixClass phoenixClass = (PhoenixClass) session.getAttribute("loggedInClass");
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents");
        if (Objects.isNull(personEntity) || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + phoenixClass.getClassId() + "&error=true");
            return modelAndView;
        }
        personEntity.setPhoenixClass(phoenixClass);
        personRepository.save(personEntity);
        phoenixClass.getPersons().add(personEntity);
        phoenixClassRepository.save(phoenixClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId=" + phoenixClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        PhoenixClass phoenixClass = (PhoenixClass) session.getAttribute("loggedInClass");
        Optional<Person> personEntity = personRepository.findById(personId);
        personEntity.get().setPhoenixClass(null);
        phoenixClass.getPersons().remove(personEntity.get());
        PhoenixClass phoenixClassSaved = phoenixClassRepository.save(phoenixClass);
        session.setAttribute("loggedInClass", phoenixClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId=" + phoenixClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model) {
        // List<Courses> courses = coursesRepository.findByOrderByNameDesc();
        //List<Courses> courses = coursesRepository.findByOrderByFees();
        List<Courses> courses = coursesRepository.findAll(Sort.by("name").ascending());
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("course", new Courses());
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses course) {
        coursesRepository.save(course);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @RequestMapping("/viewStudents")
    public ModelAndView viewStudents(Model model, @RequestParam int id, HttpSession session, @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<Courses> courses = coursesRepository.findById(id);
        if (error != null) {
            errorMessage = "Invalid credentials of Students";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        session.setAttribute("currentCourse", courses.get());
        modelAndView.addObject("courses", courses.get());
        modelAndView.addObject("person", new Person());
        return modelAndView;
    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        Person personEntity = personRepository.readByEmail(person.getEmail());
        Courses courses = (Courses) session.getAttribute("currentCourse");
        ModelAndView modelAndView = new ModelAndView();
        if (Objects.isNull(personEntity) || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/viewStudents?id=" + courses.getCourseId() + "&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("currentCourse",courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model,@RequestParam int personId,HttpSession session){
        ModelAndView modelAndView=new ModelAndView();
        Courses courses=(Courses) session.getAttribute("currentCourse");
        Optional<Person> personEntity=personRepository.findById(personId);
        personEntity.get().getCourses().remove(courses);
        courses.getPersons().remove(personEntity.get());
        personRepository.save(personEntity.get());
        session.setAttribute("currentCourse",courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return  modelAndView;
    }

}
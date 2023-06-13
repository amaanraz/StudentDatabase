package com.asn2.asn2.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asn2.asn2.models.Student;
import com.asn2.asn2.models.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;


@Controller
public class StudentController {
    
    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/students/view")
    public String getAllUsers(Model model){
        System.out.println("Getting all users");
        
        List<Student> students = studentRepo.findAll();
        // end of database call
        // Sort the list of students based on their IDs
        students.sort(Comparator.comparingLong(Student::getUid));
        
        model.addAttribute("stu", students);
        return "students/showAll";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam Map<String, String> newuser, HttpServletResponse response){
        System.out.println("ADD user");

        String newName = newuser.get("name");
        String newWeightString = newuser.get("weight");
        String newHeightString = newuser.get("height");
        String newAgeString = newuser.get("age");
        String color = newuser.get("color");

        // Perform validation
        if (newName.isEmpty() || newWeightString.isEmpty() || newHeightString.isEmpty() || newAgeString.isEmpty()) {
            // Handle validation errors
            // You can redirect to the form page with error messages or display them in the same view
            return "students/error";
        }

         // Convert string inputs to integers
        int newWeight = Integer.parseInt(newWeightString);
        int newHeight = Integer.parseInt(newHeightString);
        int newAge = Integer.parseInt(newAgeString);
        studentRepo.save(new Student(newName,newAge,newWeight,newHeight,color));
        response.setStatus(201);
        return "redirect:/students/view";
    }

    @PostMapping("/students/edit")
    public String editStudent(@RequestParam Map<String, String> newuser, HttpServletResponse response){
        System.out.println("edit user");
        // find student in repo
        Student current = studentRepo.findById(Integer.parseInt(newuser.get("uid"))).get();
        String newName = newuser.get("name");
        int newWeight = Integer.parseInt(newuser.get("weight"));
        int newHeight = Integer.parseInt(newuser.get("height"));
        int newAge = Integer.parseInt(newuser.get("age"));
        String color = newuser.get("color");

        current.setName(newName);
        current.setAge(newAge);
        current.setHeight(newHeight);
        current.setWeight(newWeight);
        current.setColor(color);
        
        studentRepo.save(current);
        response.setStatus(201);
        return "redirect:/students/view";
    }

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam("studentId") String studentId, HttpServletResponse response){
        System.out.println("delete user" + studentId);
        studentRepo.deleteById(Integer.parseInt(studentId));
        // int newUID = Integer.parseInt(newuser.get("uid"));
        // studentRepo.deleteById(newUID);
        // response.setStatus(201);
        return "redirect:/students/view";
    }

    

}

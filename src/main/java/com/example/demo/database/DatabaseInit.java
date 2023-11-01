package com.example.demo.database;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import com.example.demo.model.Course;
import com.example.demo.model.HomeWork;
import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.HomeWorkRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;

import jakarta.transaction.Transactional;

@Controller
@Transactional
@Profile("default")
public class DatabaseInit implements ApplicationRunner {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    HomeWorkRepository homeWorkRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    CourseRepository courseRepository;



    @Override
    public void run(ApplicationArguments args) throws Exception {

        //Pnemos una semilla para los datos random
        Random random = new Random(42);

        //Generacion de estudiantes
        studentRepository.save(new Student("Sebastian Angarita","Sistemas",3,"juseanto@javeriana.edu.co"));
        studentRepository.save(new Student("Margarita Mendoza","Filosofia",2,"margarita@javeriana.edu.co"));
        studentRepository.save(new Student("Pedro Claver","Matematicas",3,"pedro@javeriana.edu.co"));
        studentRepository.save(new Student("Camilo Cabra","Sistemas",8,"Camilo@javeriana.edu.co"));
        studentRepository.save(new Student("Alejandro Herrera","Sociologia",8,"Alejandro@javeriana.edu.co"));
    
        //Generacion de tareas
        homeWorkRepository.save(new HomeWork("Ejericios ecuaciones", "Terminar taller"));
        homeWorkRepository.save(new HomeWork("Ejericios mate", "Terminar taller"));
        homeWorkRepository.save(new HomeWork("Ejericios sociales", "Terminar taller"));
        homeWorkRepository.save(new HomeWork("Ejericios filosofia", "Terminar taller"));
        homeWorkRepository.save(new HomeWork("Proyecto final", "Terminar taller"));
        homeWorkRepository.save(new HomeWork("Ejericios ecuaciones 2", "Terminar taller"));
        homeWorkRepository.save(new HomeWork("Ejericios mate 2", "Terminar taller"));
        homeWorkRepository.save(new HomeWork("Ejericios sociales 2", "Terminar taller"));
        homeWorkRepository.save(new HomeWork("Ejericios filosofia 2", "Terminar taller"));
        homeWorkRepository.save(new HomeWork("Proyecto final 2", "Terminar taller"));

        //Generacion profesores
        teacherRepository.save(new Teacher("Anabel", "Anabel@javeriana.edu.co", "123456"));
        teacherRepository.save(new Teacher("Pavlich", "Pavlich@javeriana.edu.co", "123456"));
        teacherRepository.save(new Teacher("Bustacara", "Bustacara@javeriana.edu.co", "123456"));

        //Generacion de cursos
        courseRepository.save(new Course("BASES DE DATOS"));
        courseRepository.save(new Course("ESTRUCTURAS DE DATOS"));
        courseRepository.save(new Course("CIENCIAS BASISCAS"));
        courseRepository.save(new Course("CALCULO DIFERENCIAL"));
        courseRepository.save(new Course("CALCULO INTEGRAL"));
        courseRepository.save(new Course("CALCULO MULTIVARIABLE"));

        //Asignacion de tareas a estudiantes
        int CANTIDAD_ESTUDIANTES = 5;
        for(HomeWork homeWork: homeWorkRepository.findAll()){
            int randomNum = random.nextInt(1, CANTIDAD_ESTUDIANTES + 1);
            Long search = Long.valueOf(randomNum);
            Student student = studentRepository.findById(search).get();
            homeWork.setEstudiante(student);
        }

        //Asignacion de profesores y estudiantes a cursos
        int CANTIDAD_PROFESORES = 3;
        for(Course course: courseRepository.findAll()){
            int id_profesor = random.nextInt(1, CANTIDAD_PROFESORES + 1);
            int id_estudiante = random.nextInt(1, CANTIDAD_ESTUDIANTES + 1);
            Student student = studentRepository.findById(Long.valueOf(id_estudiante)).get();
            Teacher teacher = teacherRepository.findById(Long.valueOf(id_profesor)).get();
            course.setStudent(student);
            course.setTeacher(teacher);
        }
    }

}

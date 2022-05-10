package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.*;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private Service service;

    private StudentXMLRepository fileRepository1;
    private HomeworkXMLRepository fileRepository2;
    private GradeXMLRepository fileRepository3;

    @BeforeEach
    void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    void testFindAllStudents() {
        assertTrue(fileRepository1.findAll() == service.findAllStudents());
    }

    @Test
    void testSaveStudent() {
        long count = StreamSupport.stream(fileRepository1.findAll().spliterator(), false).count();
        service.saveStudent("13", "Feri", 533);
        assertEquals(StreamSupport.stream(service.findAllStudents().spliterator(), false).count(), count + 1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3"})
    void testUpdateHomework(String id) {
        Homework homework = new Homework(id, "nem", 11, 9);
        service.updateHomework(id,  "nem", 11, 9);
        assertEquals(homework, fileRepository2.findOne(id));
    }

    @Test
    void testUpdateHomework2() {
        assertEquals(0, service.updateHomework("-1",  "nem", 11, 9));
    }

    @Test
    void testDeleteStudent() {
        service.deleteStudent("11");
        Student student = fileRepository1.findOne("11");
        assertNull(student);
    }

}
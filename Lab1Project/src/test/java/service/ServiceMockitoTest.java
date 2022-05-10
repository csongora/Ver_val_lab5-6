package service;

import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class ServiceMockitoTest {
    private static Service service;

    private static StudentXMLRepository studentMockedRepository;
    private static HomeworkXMLRepository homeworkMockedRepository;
    private static GradeXMLRepository gradeMockedRepository;

    @BeforeAll
    static void init() {
        studentMockedRepository = mock(StudentXMLRepository.class);
        homeworkMockedRepository = mock(HomeworkXMLRepository.class);
        gradeMockedRepository = mock(GradeXMLRepository.class);

        service = new Service(studentMockedRepository, homeworkMockedRepository, gradeMockedRepository);
    }

    @Test
    public void testFindAllStudents() {
        Student student1 = new Student("44", "Bob", 333);
        Student student2 = new Student("45", "Sue", 333);
        ArrayList<Student> students = new ArrayList<>();
        when(studentMockedRepository.findAll()).thenReturn(students);
        assertSame(studentMockedRepository.findAll(), service.findAllStudents());
        verify(studentMockedRepository, times(2)).findAll();
    }

    @Test
    public void testUpdateHomework2() {
        when(homeworkMockedRepository.update(new Homework("-1", anyString(), 11, 9))).thenReturn(null);
        assertEquals(0, service.updateHomework("-1",  "randomString", 11, 9));
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student("1", "Ferike", 333);
        when(studentMockedRepository.delete("1")).thenReturn(student);
        assertEquals(1, service.deleteStudent("1"));
    }

}

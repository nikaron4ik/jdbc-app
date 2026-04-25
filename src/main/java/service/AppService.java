package service;

import dao.*;
import model.Course;
import model.Enrollment;
import model.Student;
import model.Teacher;

import java.util.List;

public class AppService {
    private final TeacherDao teacherDao = new TeacherDaoImpl();
    private final CourseDao courseDao = new CourseDaoImpl();
    private final StudentDao studentDao = new StudentDaoImpl();
    private final EnrollmentDao enrollmentDao = new EnrollmentDaoImpl();

    public void createTeacher(Teacher teacher) {
        teacherDao.save(teacher);
    }
    public List<Teacher> getAllTeachers() {
        return teacherDao.findAll();
    }
    public Teacher getTeacherById(Long id) {
        return teacherDao.findById(id);
    }
    public void updateTeacher(Teacher teacher) {
        teacherDao.update(teacher);
    }
    public void deleteTeacher(Long id) {
        teacherDao.delete(id);
    }
    public List<String> getTeachersWithJoin() {
        return teacherDao.findAllWithJoin();
    }

    public void createCourse(Course course) {
        courseDao.save(course);
    }
    public List<Course> getAllCourses() {
        return courseDao.findAll();
    }
    public Course getCourseById(Long id) {
        return courseDao.findById(id);
    }
    public void updateCourse(Course course) {
        courseDao.update(course);
    }
    public void deleteCourse(Long id) {
        courseDao.delete(id);
    }
    public List<String> getCoursesWithJoin() {
        return courseDao.findAllWithJoin();
    }

    public void createStudent(Student student) {
        studentDao.save(student);
    }
    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }
    public Student getStudentById(Long id) {
        return studentDao.findById(id);
    }
    public void updateStudent(Student student) {
        studentDao.update(student);
    }
    public void deleteStudent(Long id) {
        studentDao.delete(id);
    }
    public List<String> getStudentsWithJoin() {
        return studentDao.findAllWithJoin();
    }

    public void createEnrollment(Enrollment enrollment) {
        enrollmentDao.save(enrollment);
    }
    public List<Enrollment> getAllEnrollments() {
        return enrollmentDao.findAll();
    }
    public Enrollment getEnrollmentById(Long id) {
        return enrollmentDao.findById(id);
    }
    public void updateEnrollment(Enrollment enrollment) {
        enrollmentDao.update(enrollment);
    }
    public void deleteEnrollment(Long id) {
        enrollmentDao.delete(id);
    }
    public List<String> getEnrollmentsWithJoin() {
        return enrollmentDao.findAllWithJoin();
    }
}

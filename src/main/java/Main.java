import model.Course;
import model.Enrollment;
import model.Student;
import model.Teacher;
import service.AppService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final AppService app = new AppService();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = readInt("> Ваш выбор: ");

            try {
                switch (choice) {
                    case 1 -> save();
                    case 2 -> findAll();
                    case 3 -> findById();
                    case 4 -> update();
                    case 5 -> delete();
                    case 6 -> findAllWithJoin();
                    case 0 -> {
                        System.out.println("Программа завершена");
                        return;
                    }
                    default -> System.out.println("Выбранного пункта нет");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }

    private static void printMenu() {
        System.out.println("=== Приложение курсов (JDBC) ===");
        System.out.println("1. Создать запись");
        System.out.println("2. Показать все записи");
        System.out.println("3. Найти запись по ID");
        System.out.println("4. Обновить запись");
        System.out.println("5. Удалить запись");
        System.out.println("6. Показать данные с JOIN (связанные таблицы)");
        System.out.println("0. Выход");
    }

    private static void save() {
        int entity = chooseEntity();
        switch (entity) {
            case 1 -> saveTeacher();
            case 2 -> saveCourse();
            case 3 -> saveStudent();
            case 4 -> saveEnrollment();
            default -> System.out.println("Некорректный выбор");
        }
    }

    private static void saveTeacher() {
        String name = readString("Name: ");
        String email = readString("Email: ");
        String specialization = readString("Specialization: ");
        LocalDate hireDate = readDate("Hire date (yyyy-mm-dd): ");

        app.createTeacher(new Teacher(null, name, email, specialization, hireDate));
        System.out.println("Teacher created!");
    }

    private static void saveCourse() {
        String title = readString("Title: ");
        String description = readString("Description: ");
        Long teacherId = readLong("Teacher id: ");
        LocalDate startDate = readDate("Start date (yyyy-mm-dd): ");

        app.createCourse(new Course(null, title, description, teacherId, startDate));
        System.out.println("Course created!");
    }

    private static void saveStudent() {
        String name = readString("Name: ");
        String email = readString("Email: ");
        LocalDate birthDate = readDate("Birth date (yyyy-mm-dd): ");
        LocalDate enrollmentDate = readDate("Enrollment date (yyyy-mm-dd): ");

        app.createStudent(new Student(null, name, email, birthDate, enrollmentDate));
        System.out.println("Student created!");
    }

    private static void saveEnrollment() {
        Long studentId = readLong("Student id: ");
        Long courseId = readLong("Course id: ");
        LocalDate enrolledAt = readDate("Enrolled at (yyyy-mm-dd): ");
        Integer grade = readNullableInt("Grade (or Enter): ");

        app.createEnrollment(new Enrollment(null, studentId, courseId, enrolledAt, grade));
        System.out.println("Enrollment created!");
    }

    private static void findAll() {
        int entity = chooseEntity();
        switch (entity) {
            case 1 -> printList(app.getAllTeachers());
            case 2 -> printList(app.getAllCourses());
            case 3 -> printList(app.getAllStudents());
            case 4 -> printList(app.getAllEnrollments());
            default -> System.out.println("Некорректный выбор");
        }
    }

    private static void findById() {
        int entity = chooseEntity();
        Long id = readLong("id: ");
        switch (entity) {
            case 1 -> System.out.println(app.getTeacherById(id));
            case 2 -> System.out.println(app.getCourseById(id));
            case 3 -> System.out.println(app.getStudentById(id));
            case 4 -> System.out.println(app.getEnrollmentById(id));
            default -> System.out.println("Некорректный выбор");
        }
    }

    private static void update() {
        int entity = chooseEntity();
        switch (entity) {
            case 1 -> updateTeacher();
            case 2 -> updateCourse();
            case 3 -> updateStudent();
            case 4 -> updateEnrollment();
            default -> System.out.println("Некорректный выбор");
        }
    }

    private static void updateTeacher() {
        Long id = readLong("id: ");
        String name = readString("Name: ");
        String email = readString("Email: ");
        String specialization = readString("Specialization: ");
        LocalDate hireDate = readDate("Hire date (yyyy-mm-dd): ");

        app.updateTeacher(new Teacher(id, name, email, specialization, hireDate));
        System.out.println("Teacher updated!");
    }

    private static void updateCourse() {
        Long id = readLong("id: ");
        String title = readString("Title: ");
        String description = readString("Description: ");
        Long teacherId = readLong("Teacher id: ");
        LocalDate startDate = readDate("Start date (yyyy-mm-dd): ");

        app.updateCourse(new Course(id, title, description, teacherId, startDate));
        System.out.println("Course updated!");
    }

    private static void updateStudent() {
        Long id = readLong("id: ");
        String name = readString("Name: ");
        String email = readString("Email: ");
        LocalDate birthDate = readDate("Birth date (yyyy-mm-dd): ");
        LocalDate enrollmentDate = readDate("Enrollment date (yyyy-mm-dd): ");

        app.updateStudent(new Student(id, name, email, birthDate, enrollmentDate));
        System.out.println("Student updated!");
    }

    private static void updateEnrollment() {
        Long id = readLong("id: ");
        Long studentId = readLong("Student id: ");
        Long courseId = readLong("Course id: ");
        LocalDate enrolledAt = readDate("Enrolled at (yyyy-mm-dd): ");
        Integer grade = readNullableInt("Grade (or Enter): ");

        app.updateEnrollment(new Enrollment(id, studentId, courseId, enrolledAt, grade));
        System.out.println("Enrollment updated!");
    }

    private static void delete() {
        int entity = chooseEntity();
        Long id = readLong("id: ");
        switch (entity) {
            case 1 -> app.deleteTeacher(id);
            case 2 -> app.deleteCourse(id);
            case 3 -> app.deleteStudent(id);
            case 4 -> app.deleteEnrollment(id);
            default -> {
                System.out.println("Некорректный выбор");
                return;
            }
        }
        System.out.println("Deleted!");
    }

    private static void findAllWithJoin() {
        int entity = chooseEntity();
        switch (entity) {
            case 1 -> printList(app.getTeachersWithJoin());
            case 2 -> printList(app.getCoursesWithJoin());
            case 3 -> printList(app.getStudentsWithJoin());
            case 4 -> printList(app.getEnrollmentsWithJoin());
            default -> System.out.println("Некорректный выбор");
        }
    }

    private static int chooseEntity() {
        System.out.println("Выберите сущность:");
        System.out.println("1. Teacher");
        System.out.println("2. Course");
        System.out.println("3. Student");
        System.out.println("4. Enrollment");
        return readInt("> Ваш выбор: ");
    }

    private static void printList(List<?> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("Данных не найдено.");
            return;
        }
        list.forEach(System.out::println);
    }

    private static String readString(String text) {
        while (true) {
            System.out.print(text);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Ввод не может быть пустым!");
        }
    }

    private static int readInt(String text) {
        while (true) {
            try {
                System.out.print(text);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    private static Long readLong(String text) {
        while (true) {
            try {
                System.out.print(text);
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число!");
            }
        }
    }

    private static Integer readNullableInt(String text) {
        while (true) {
            try {
                System.out.print(text);
                String s = sc.nextLine().trim();
                if (s.isEmpty()) return null;
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число (или Enter)!");
            }
        }
    }

    private static LocalDate readDate(String text) {
        while (true) {
            try {
                System.out.print(text);
                return LocalDate.parse(sc.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты. Пример: 2026-03-26");
            }
        }
    }

}

package dao;

import db.ConnectionManager;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public void save(Student student) {
        String sql = "INSERT INTO students (name, email, birth_date, enrollment_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, student.getName());
                stmt.setString(2, student.getEmail());
                stmt.setDate(3, Date.valueOf(student.getBirthDate()));
                stmt.setDate(4, Date.valueOf(student.getEnrollmentDate()));

                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Student saving error", e);
        }
    }

    @Override
    public List<Student> findAll() {
        String sql = "SELECT id, name, email, birth_date, enrollment_date FROM students";
        List<Student> list = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getDate("enrollment_date").toLocalDate()
                );
                list.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Finding all students error", e);
        }

        return list;
    }

    @Override
    public Student findById(Long id) {
        String sql = "SELECT id, name, email, birth_date, enrollment_date FROM students WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getDate("birth_date").toLocalDate(),
                            rs.getDate("enrollment_date").toLocalDate()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Finding student by id error", e);
        }
        return null;
    }

    @Override
    public void update(Student student) {
        String sql = "UPDATE students SET name = ?, email = ?, birth_date = ?, enrollment_date = ? WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, student.getName());
                stmt.setString(2, student.getEmail());
                stmt.setDate(3, Date.valueOf(student.getBirthDate()));
                stmt.setDate(4, Date.valueOf(student.getEnrollmentDate()));
                stmt.setLong(5, student.getId());

                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Student updating error", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);

                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Student deleting error", e);
        }
    }

    @Override
    public List<String> findAllWithJoin() {
        String sql = """
                    SELECT students.id AS student_id,
                           students.name AS student_name,
                           courses.id AS course_id,
                           courses.title AS course_title,
                           enrollments.enrolled_at,
                           enrollments.grade
                    FROM enrollments
                    JOIN students ON students.id = enrollments.student_id
                    JOIN courses ON courses.id = enrollments.course_id
                    ORDER BY students.id, courses.title
                """;

        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String row = String.format(
                        "student_id=%d, student_name=%s, course_id=%d, course=%s, enrolled_at=%s, grade=%s",
                        rs.getLong("student_id"),
                        rs.getString("student_name"),
                        rs.getLong("course_id"),
                        rs.getString("course_title"),
                        rs.getDate("enrolled_at"),
                        rs.getObject("grade")
                );
                list.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Finding all students error", e);
        }

        return list;
    }
}

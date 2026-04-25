package dao;

import db.ConnectionManager;
import model.Enrollment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDaoImpl implements EnrollmentDao{
    @Override
    public void save(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments (student_id, course_id, enrolled_at, grade) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, enrollment.getStudentId());
                stmt.setLong(2, enrollment.getCourseId());
                stmt.setDate(3, Date.valueOf(enrollment.getEnrolledAt()));
                stmt.setObject(4, enrollment.getGrade());

                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Enrollment saving error", e);
        }
    }

    @Override
    public List<Enrollment> findAll() {
        String sql = "SELECT id, student_id, course_id, enrolled_at, grade FROM enrollments";
        List<Enrollment> list = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Enrollment enrollment = new Enrollment(
                        rs.getLong("id"),
                        rs.getLong("student_id"),
                        rs.getLong("course_id"),
                        rs.getDate("enrolled_at").toLocalDate(),
                        (Integer) rs.getObject("grade")
                );
                list.add(enrollment);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Finding all enrollments error", e);
        }

        return list;
    }

    @Override
    public Enrollment findById(Long id) {
        String sql = "SELECT id, student_id, course_id, enrolled_at, grade FROM enrollments WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Enrollment(
                            rs.getLong("id"),
                            rs.getLong("student_id"),
                            rs.getLong("course_id"),
                            rs.getDate("enrolled_at").toLocalDate(),
                            (Integer) rs.getObject("grade")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Finding enrollment by id error", e);
        }
        return null;
    }

    @Override
    public void update(Enrollment enrollment) {
        String sql = "UPDATE enrollments SET student_id = ?, course_id = ?, enrolled_at = ?, grade = ? WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, enrollment.getStudentId());
                stmt.setLong(2, enrollment.getCourseId());
                stmt.setDate(3, Date.valueOf(enrollment.getEnrolledAt()));
                stmt.setObject(4, enrollment.getGrade());
                stmt.setLong(5, enrollment.getId());

                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Enrollment updating error", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM enrollments WHERE id = ?";

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
            throw new RuntimeException("Enrollment deleting error", e);
        }
    }

    @Override
    public List<String> findAllWithJoin() {
        String sql = """
                    SELECT enrollments.id AS enrollment_id,
                           students.name AS student_name,
                           courses.title AS course_title,
                           enrollments.enrolled_at,
                           enrollments.grade
                    FROM enrollments
                    JOIN students ON students.id = enrollments.student_id
                    JOIN courses ON courses.id = enrollments.course_id
                    ORDER BY enrollment_id
                """;

        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String row = String.format(
                        "enrollment_id=%d, student_name=%s, course=%s, enrolled_at=%s, grade=%s",
                        rs.getLong("enrollment_id"),
                        rs.getString("student_name"),
                        rs.getString("course_title"),
                        rs.getString("enrolled_at"),
                        rs.getObject("grade")
                );
                list.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Finding all enrollments error", e);
        }

        return list;
    }
}

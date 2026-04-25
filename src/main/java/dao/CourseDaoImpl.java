package dao;

import db.ConnectionManager;
import model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao{
    @Override
    public void save(Course course) {
        String sql = "INSERT INTO courses (title, description, teacher_id, start_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, course.getTitle());
                stmt.setString(2, course.getDescription());
                stmt.setLong(3, course.getTeacherId());
                stmt.setDate(4, Date.valueOf(course.getStartDate()));

                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Course saving error", e);
        }
    }

    @Override
    public List<Course> findAll() {
        String sql = "SELECT id, title, description, teacher_id, start_date FROM courses";
        List<Course> list = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Course course = new Course(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getLong("teacher_id"),
                        rs.getDate("start_date").toLocalDate()
                );
                list.add(course);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Finding all courses error", e);
        }

        return list;
    }

    @Override
    public Course findById(Long id) {
        String sql = "SELECT id, title, description, teacher_id, start_date FROM courses WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getLong("teacher_id"),
                            rs.getDate("start_date").toLocalDate()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Finding course by id error", e);
        }
        return null;
    }

    @Override
    public void update(Course course) {
        String sql = "UPDATE courses SET title = ?, description = ?, teacher_id = ?, start_date = ? WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, course.getTitle());
                stmt.setString(2, course.getDescription());
                stmt.setLong(3, course.getTeacherId());
                stmt.setDate(4, Date.valueOf(course.getStartDate()));
                stmt.setLong(5, course.getId());

                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Course updating error", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM courses WHERE id = ?";

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
            throw new RuntimeException("Course deleting error", e);
        }
    }

    @Override
    public List<String> findAllWithJoin() {
        String sql = """
                    SELECT courses.id AS course_id,
                           courses.title AS course_title,
                           teachers.id AS teacher_id,
                           teachers.name AS teacher_name,
                           courses.start_date
                    FROM courses
                    LEFT JOIN teachers ON teachers.id = courses.teacher_id
                    ORDER BY course_id
                """;

        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String row = String.format(
                        "course_id=%d, course=%s, teacher_id=%d, teacher_name=%s, start_date=%s",
                        rs.getLong("course_id"),
                        rs.getString("course_title"),
                        rs.getLong("teacher_id"),
                        rs.getString("teacher_name"),
                        rs.getDate("start_date")
                );
                list.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Finding all courses error", e);
        }

        return list;
    }
}
package dao;

import db.ConnectionManager;
import model.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDaoImpl implements TeacherDao {
    @Override
    public void save(Teacher teacher) {
        String sql = "INSERT INTO teachers (name, email, specialization, hire_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, teacher.getName());
                stmt.setString(2, teacher.getEmail());
                stmt.setString(3, teacher.getSpecialization());
                stmt.setDate(4, Date.valueOf(teacher.getHireDate()));

                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Teacher saving error", e);
        }
    }

    @Override
    public List<Teacher> findAll() {
        String sql = "SELECT id, name, email, specialization, hire_date FROM teachers";
        List<Teacher> list = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("specialization"),
                        rs.getDate("hire_date").toLocalDate()
                );
                list.add(teacher);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Finding all teachers error", e);
        }

        return list;
    }

    @Override
    public Teacher findById(Long id) {
        String sql = "SELECT id, name, email, specialization, hire_date FROM teachers WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Teacher(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("specialization"),
                            rs.getDate("hire_date").toLocalDate()
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Finding teachers by id error", e);
        }
        return null;
    }

    @Override
    public void update(Teacher teacher) {
        String sql = "UPDATE teachers SET name = ?, email = ?, specialization = ?, hire_date = ? WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, teacher.getName());
                stmt.setString(2, teacher.getEmail());
                stmt.setString(3, teacher.getSpecialization());
                stmt.setDate(4, Date.valueOf(teacher.getHireDate()));
                stmt.setLong(5, teacher.getId());

                stmt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Teacher updating error", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM teachers WHERE id = ?";

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
            throw new RuntimeException("Teacher deleting error", e);
        }
    }

    @Override
    public List<String> findAllWithJoin() {
        String sql = """
                    SELECT teachers.id AS teacher_id,
                           teachers.name AS teacher_name,
                           courses.id AS course_id,
                           courses.title AS course_title,
                           courses.start_date
                    FROM teachers
                    LEFT JOIN courses ON courses.teacher_id = teachers.id
                    ORDER BY teachers.id, courses.title
                """;

        List<String> list = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String row = String.format(
                        "teacher_id=%d, teacher_name=%s, course_id=%d, course=%s, start_date=%s",
                        rs.getLong("teacher_id"),
                        rs.getString("teacher_name"),
                        rs.getLong("course_id"),
                        rs.getString("course_title"),
                        rs.getDate("start_date")
                );
                list.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Finding all teachers error", e);
        }

        return list;
    }
}

package model;

import java.time.LocalDate;

public class Enrollment {
    private Long id;
    private Long studentId;
    private Long courseId;
    private LocalDate enrolledAt;
    private Integer grade;

    public Enrollment() {
    }

    public Enrollment(Long id, Long studentId, Long courseId, LocalDate enrolledAt, Integer grade) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrolledAt = enrolledAt;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public LocalDate getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDate enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", enrolledAt=" + enrolledAt +
                ", grade=" + grade +
                '}';
    }
}

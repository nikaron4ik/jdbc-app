package model;

import java.time.LocalDate;

public class Course {
    private Long id;
    private String title;
    private String description;
    private Long teacherId;
    private LocalDate startDate;

    public Course() {
    }

    public Course(Long id, String title, String description, Long teacherId, LocalDate startDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.teacherId = teacherId;
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", teacherId=" + teacherId +
                ", startDate=" + startDate +
                '}';
    }
}

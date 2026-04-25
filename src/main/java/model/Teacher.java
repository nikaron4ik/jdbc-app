package model;

import java.time.LocalDate;

public class Teacher {
    private Long id;
    private String name;
    private String email;
    private String specialization;
    private LocalDate hireDate;

    public Teacher() {
    }

    public Teacher(Long id, String name, String email, String specialization, LocalDate hireDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.specialization = specialization;
        this.hireDate = hireDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", specialization='" + specialization + '\'' +
                ", hireDate=" + hireDate +
                '}';
    }
}

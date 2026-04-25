package model;


import java.time.LocalDate;

public class Student {
    private Long id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private LocalDate enrollmentDate;

    public Student() {
    }

    public Student(Long id, String name, String email, LocalDate birthDate, LocalDate enrollmentDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.enrollmentDate = enrollmentDate;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", enrollmentDate=" + enrollmentDate +
                '}';
    }
}

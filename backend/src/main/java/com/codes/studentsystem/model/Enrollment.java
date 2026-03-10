package com.codes.studentsystem.model;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "enrollment",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_enrollment_student_course",
                columnNames = {"user_id", "course_id"}
        ))
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Enrollment belongs to the authenticated user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    // Many enrollments per course
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EnrollmentStatus status = EnrollmentStatus.ENROLLED;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column
    private Instant updatedAt;

    public Enrollment() {}
    public Enrollment(AppUser user, Course course) {
        this.user = user;
        this.course = course;
        this.status = EnrollmentStatus.ENROLLED;
        this.createdAt = Instant.now();
    }


    public Long getId() { return id; }
    public AppUser getUser() { return user; }
    public Course getCourse() { return course; }

    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }

    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}

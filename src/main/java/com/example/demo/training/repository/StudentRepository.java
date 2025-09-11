package com.example.demo.training.repository;

import com.example.demo.training.dto.StudentInfoDTO;
import com.example.demo.training.entity.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Integer> {
  // Lấy 1 student kèm profile + class + courses (JOIN FETCH tránh N+1)
  @Query("""
         select s
         from Student s
         left join fetch s.profile
         join fetch s.classRoom
         left join fetch s.courses
         where s.id = :id
         """)
  Optional<Student> findByIdWithAll(@Param("id") Integer id);

  @Query("""
        select new com.example.demo.training.dto.StudentInfoDTO(
            s.id,
            s.name,
            cr.name,
            p.email,
            p.phoneNumber
        )
        from Student s
        join s.classRoom cr
        left join s.profile p
        """)
  List<StudentInfoDTO> findStudentWithClassAndProfile();
}

package com.nal.pdfcvbuilder.repositories;

import com.nal.pdfcvbuilder.entities.Resume;
import com.nal.pdfcvbuilder.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Resume findResumeByUser(User user);
}

package com.winggs.course.repo;

import com.winggs.course.modal.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepo extends JpaRepository<Certificate, String> {
}

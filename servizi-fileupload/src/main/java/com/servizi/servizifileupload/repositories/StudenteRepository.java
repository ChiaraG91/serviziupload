package com.servizi.servizifileupload.repositories;

import com.servizi.servizifileupload.entities.Studente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudenteRepository extends JpaRepository<Studente,Long> {
}

package com.example.BackendDislinkt.repository;

import com.example.BackendDislinkt.model.Komentar;
import com.example.BackendDislinkt.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KomentarRepository extends JpaRepository<Komentar, Integer> {
    List<Komentar> findAll();
    Komentar save(Komentar komentar);
    void deleteById(Integer id);
}

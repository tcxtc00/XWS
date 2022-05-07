package com.example.BackendDislinkt.service;

import com.example.BackendDislinkt.model.Komentar;
import com.example.BackendDislinkt.repository.KomentarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KomentarService {
    @Autowired
    private KomentarRepository komentarRepository;
    public Komentar save(Komentar komentar) {
        return komentarRepository.save(komentar);
    }

    public List<Komentar> findAll() {
        return komentarRepository.findAll(); }

    public void remove(Integer id) {
        komentarRepository.deleteById(id);
    }
}

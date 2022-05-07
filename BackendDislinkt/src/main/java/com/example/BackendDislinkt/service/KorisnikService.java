package com.example.BackendDislinkt.service;

import com.example.BackendDislinkt.model.Korisnik;
import com.example.BackendDislinkt.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KorisnikService {
    @Autowired
    private KorisnikRepository korisnikRepository;

    public Korisnik save(Korisnik korisnik) {
        return korisnikRepository.save(korisnik);
    }

    public List<Korisnik> findAll() {
        return korisnikRepository.findAll(); }

    public void remove(Integer id) {
        korisnikRepository.deleteById(id);
    }
}

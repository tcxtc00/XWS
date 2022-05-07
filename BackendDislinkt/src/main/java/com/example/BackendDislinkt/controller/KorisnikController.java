package com.example.BackendDislinkt.controller;

import com.example.BackendDislinkt.dto.KorisnikDTO;
import com.example.BackendDislinkt.model.Korisnik;
import com.example.BackendDislinkt.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/korisnik")
public class KorisnikController {
    @Autowired
    private KorisnikService korisnikService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> saveKorisnik(@RequestBody KorisnikDTO korisnikDTO, HttpServletRequest httpServletRequest) {
        try {
            Korisnik korisnik = new Korisnik();
            List<Korisnik> korisniks = korisnikService.findAll();
            if(korisniks!= null){
                for(Korisnik k : korisniks){
                    if(k.getKorisnickoIme().equals(korisnikDTO.getKorisnickoIme()) || k.getKorisnickoIme().equals("") || k.getLozinka().equals("")){
                        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
                    }
                }
            }
            korisnik.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
            korisnik.setLozinka(korisnikDTO.getLozinka());
            korisnik.setIme(korisnikDTO.getIme());
            korisnik.setPrezime(korisnikDTO.getPrezime());
            korisnik.setBrojTelefona(korisnikDTO.getBrojTelefona());
            korisnik.setDatumRodjenja(korisnikDTO.getDatumRodjenja());
            korisnik.setEmail(korisnikDTO.getEmail());
            korisnik.setPol(korisnikDTO.getPol());
            korisnik.setBiografija(korisnikDTO.getBiografija());
            korisnik.setAktivan(Boolean.TRUE);

            korisnikService.save(korisnik);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

    }
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Korisnik>> getKorsnike() {
        List<Korisnik> korisniks = korisnikService.findAll();
        if(korisniks != null)
        {
            return new ResponseEntity<>(korisniks, HttpStatus.OK);
        }
        return new ResponseEntity<>(korisniks, HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteKorisnik(@PathVariable Integer id) {
        korisnikService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/login",produces = "application/json")
    public ResponseEntity<Korisnik> getKorisnik(@RequestBody KorisnikDTO korisnikDTO) {
        List<Korisnik> korisniks = korisnikService.findAll();
        if(korisniks != null)
        {
            for(Korisnik korisnik : korisniks){
                if(korisnik.getKorisnickoIme().equals(korisnikDTO.getKorisnickoIme()) && korisnik.getLozinka().equals(korisnikDTO.getLozinka()))
                    return new ResponseEntity<>(korisnik, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/{korisnickoIme}", produces = "application/json")
    public ResponseEntity<Korisnik> getKorisnik(@PathVariable String korisnickoIme) {
        List<Korisnik> korisniks = korisnikService.findAll();
        if(korisniks != null)
        {
            for(Korisnik k : korisniks){
                if(k.getKorisnickoIme().equals(korisnickoIme)){
                    return new ResponseEntity<>(k, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/public", produces = "application/json")
    public ResponseEntity<List<Korisnik>> getKorisnikPublic() {
        List<Korisnik> korisniks = korisnikService.findAll();
        List<Korisnik> korisniksPublic = new ArrayList<>();
        if(korisniks != null)
        {
            for(Korisnik k : korisniks){
                if(k.isAktivan()){
                    korisniksPublic.add(k);
                }
            }
            return new ResponseEntity<>(korisniksPublic, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
    }

    @PutMapping(value = "/{korisnickoIme}",consumes = "application/json")
    public ResponseEntity<Integer> updateKorisnik(@PathVariable String korisnickoIme, @RequestBody KorisnikDTO korisnikDTO, HttpServletRequest httpServletRequest) {
        try {
            List<Korisnik> korisniks = korisnikService.findAll();
            Korisnik korisnik = null;
            for(Korisnik k : korisniks)
            {
                if(k.getKorisnickoIme().equals(korisnickoIme))
                {
                    korisnik = k;
                }
            }
            if(korisnik != null)
            {
                korisnik.setIme(korisnikDTO.getIme());
                korisnik.setPrezime(korisnikDTO.getPrezime());
                korisnik.setBiografija(korisnikDTO.getBiografija());
                korisnik.setPol(korisnikDTO.getPol());
                korisnik.setEmail(korisnikDTO.getEmail());
                korisnik.setBrojTelefona(korisnikDTO.getBrojTelefona());
                korisnik.setDatumRodjenja(korisnikDTO.getDatumRodjenja());
                korisnik.setRadnoIskustvo(korisnikDTO.getRadnoIskustvo());
                korisnik.setInteresovanja(korisnikDTO.getInteresovanja());
                korisnik.setObrazovanje(korisnikDTO.getObrazovanje());
                korisnik.setVestina(korisnikDTO.getVestina());

                for(Korisnik k : korisniks)
                {
                    if(!k.getKorisnickoIme().equals(korisnikDTO.getKorisnickoIme()))
                    {
                        korisnik.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
                    }
                    else
                    {
                        return new ResponseEntity<>(0,HttpStatus.NOT_MODIFIED);
                    }
                }

                korisnikService.save(korisnik);
                return new ResponseEntity<>(korisnik.getId(), HttpStatus.CREATED);
            }
            else
            {
                return new ResponseEntity<>(0,HttpStatus.NOT_MODIFIED);
            }
        }catch(Exception e){
            return new ResponseEntity<>(0,HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping(value = "/{korisnickoIme}/{kogaPratim}",consumes = "application/json")
    public ResponseEntity<Integer> zapracivanje(@PathVariable String korisnickoIme, @PathVariable String kogaPratim, HttpServletRequest httpServletRequest) {
        try {
            if(korisnickoIme.equals(kogaPratim))
            {
                return new ResponseEntity<>(0,HttpStatus.NOT_MODIFIED);
            }
            List<Korisnik> korisniks = korisnikService.findAll();
            Korisnik korisnik = null;
            Korisnik korisnikKogaPratim = null;
            for(Korisnik k : korisniks)
            {
                if(k.getKorisnickoIme().equals(korisnickoIme))
                {
                    korisnik = k;
                }
                if(k.getKorisnickoIme().equals(kogaPratim))
                {
                    korisnikKogaPratim = k;
                }
            }
            if(korisnik != null && korisnikKogaPratim != null)
            {
                for(Korisnik k : korisnik.getKogaPratim())
                {
                    if(k == korisnikKogaPratim)
                    {
                        return new ResponseEntity<>(0,HttpStatus.NOT_MODIFIED);
                    }
                }
                korisnik.getKogaPratim().add(korisnikKogaPratim);
                korisnik.setKogaPratim(korisnik.getKogaPratim());
                korisnikService.save(korisnik);
                return new ResponseEntity<>(korisnik.getId(), HttpStatus.CREATED);
            }
            else
            {
                return new ResponseEntity<>(0,HttpStatus.NOT_MODIFIED);
            }
        }catch(Exception e){
            return new ResponseEntity<>(0,HttpStatus.NOT_MODIFIED);
        }
    }


}

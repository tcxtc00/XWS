package com.example.BackendDislinkt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Korisnik {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ime", nullable = false)
    private String ime;
    @Column(name = "prezime", nullable = false)
    private String prezime;
    @Column(name = "korisnickoIme", nullable = false, unique = true)
    private String korisnickoIme;
    @Column(name = "lozinka", nullable = false)
    private String lozinka;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "brojTelefona")
    private String brojTelefona;
    @Column(name = "pol")
    private String pol;
    @Column(name = "datumRodjenja")
    private String datumRodjenja;
    @Column(name = "biografija")
    private String biografija;
    @Column(name = "javan")
    private boolean javan;
    @Column(name = "aktivan")
    private boolean aktivan;
    @Column(name = "radnoIskustvo")
    private String radnoIskustvo;
    @Column(name = "obrazovanje")
    private String obrazovanje;
    @Column(name = "vestina")
    private String vestina;
    @Column(name = "interesovanja")
    private String interesovanja;

    @ManyToMany(mappedBy = "lajkovali")
    @JsonIgnore
    Set<Post> lajkovaniPostovi;

    @OneToMany(mappedBy = "korisnikId",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Komentar> komentari;

    @ManyToMany
    @JoinTable(
            name = "koMePrati",
            joinColumns = @JoinColumn(name = "korisnikId"),
            inverseJoinColumns = @JoinColumn(name = "ParentId"))
    private Set<Korisnik> koMePrati;

    @ManyToMany(mappedBy = "koMePrati")
    @JsonIgnore
    Set<Korisnik> kogaPratim;

    public Korisnik() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getBiografija() {
        return biografija;
    }

    public void setBiografija(String biografija) {
        this.biografija = biografija;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    public boolean isJavan() {
        return javan;
    }

    public void setJavan(boolean javan) {
        this.javan = javan;
    }

    public Set<Post> getLajkovaniPostovi() {
        return lajkovaniPostovi;
    }

    public void setLajkovaniPostovi(Set<Post> lajkovaniPostovi) {
        this.lajkovaniPostovi = lajkovaniPostovi;
    }

    public Set<Komentar> getKomentari() {
        return komentari;
    }

    public void setKomentari(Set<Komentar> komentari) {
        this.komentari = komentari;
    }

    public String getRadnoIskustvo() {
        return radnoIskustvo;
    }

    public void setRadnoIskustvo(String radnoIskustvo) {
        this.radnoIskustvo = radnoIskustvo;
    }

    public String getObrazovanje() {
        return obrazovanje;
    }

    public void setObrazovanje(String obrazovanje) {
        this.obrazovanje = obrazovanje;
    }

    public String getVestina() {
        return vestina;
    }

    public void setVestina(String vestina) {
        this.vestina = vestina;
    }

    public String getInteresovanja() {
        return interesovanja;
    }

    public void setInteresovanja(String interesovanja) {
        this.interesovanja = interesovanja;
    }

    public Set<Korisnik> getKoMePrati() {
        return koMePrati;
    }

    public void setKoMePrati(Set<Korisnik> koMePrati) {
        this.koMePrati = koMePrati;
    }

    public Set<Korisnik> getKogaPratim() {
        return kogaPratim;
    }

    public void setKogaPratim(Set<Korisnik> kogaPratim) {
        this.kogaPratim = kogaPratim;
    }
}

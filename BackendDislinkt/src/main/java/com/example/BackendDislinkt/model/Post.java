package com.example.BackendDislinkt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javassist.bytecode.ByteArray;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Post {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "tekst", nullable = false)
    private String tekst;
    @Column(name = "slika")
    private String slika;
    @Lob
    @Column(name = "slikaLob")
    private byte[] slikaLob;
    @Column(name = "link")
    private String link;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "objavio")
    private Korisnik objavio;
    @ManyToMany
    @JoinTable(
            name = "lajkovali",
            joinColumns = @JoinColumn(name = "postId"),
            inverseJoinColumns = @JoinColumn(name = "korisnikId"))
    private Set<Korisnik> lajkovali;
    @OneToMany(mappedBy = "postId",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Komentar> komentari;

    public Post(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Korisnik getObjavio() {
        return objavio;
    }

    public void setObjavio(Korisnik objavio) {
        this.objavio = objavio;
    }

    public Set<Korisnik> getLajkovali() {
        return lajkovali;
    }

    public void setLajkovali(Set<Korisnik> lajkovali) {
        this.lajkovali = lajkovali;
    }

    public Set<Komentar> getKomentari() {
        return komentari;
    }

    public void setKomentari(Set<Komentar> komentari) {
        this.komentari = komentari;
    }

    public byte[] getSlikaLob() {
        return slikaLob;
    }

    public void setSlikaLob(byte[] slikaLob) {
        this.slikaLob = slikaLob;
    }
}

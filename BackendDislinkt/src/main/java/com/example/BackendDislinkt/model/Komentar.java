package com.example.BackendDislinkt.model;

import javax.persistence.*;

@Entity
public class Komentar {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "tekst")
    private String tekst;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "korisnikId")
    private Korisnik korisnikId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postId")
    private Post postId;

    public Komentar(){}

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

    public Korisnik getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Korisnik korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }
}

package com.example.BackendDislinkt.dto;

import com.example.BackendDislinkt.model.Korisnik;
import javassist.bytecode.ByteArray;

public class PostDTO {
    private Integer id;
    private String tekst;
    private String slika;
    private byte[] slikaLob;
    private String link;
    private String objavio;

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

    public String getObjavio() {
        return objavio;
    }

    public void setObjavio(String objavio) {
        this.objavio = objavio;
    }

    public byte[] getSlikaLob() {
        return slikaLob;
    }

    public void setSlikaLob(byte[] slikaLob) {
        this.slikaLob = slikaLob;
    }
}

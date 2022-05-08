package com.example.BackendDislinkt.controller;

import com.example.BackendDislinkt.dto.KomentarDTO;
import com.example.BackendDislinkt.dto.KorisnikDTO;
import com.example.BackendDislinkt.dto.PostDTO;
import com.example.BackendDislinkt.model.Komentar;
import com.example.BackendDislinkt.model.Korisnik;
import com.example.BackendDislinkt.model.Post;
import com.example.BackendDislinkt.service.KomentarService;
import com.example.BackendDislinkt.service.KorisnikService;
import com.example.BackendDislinkt.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/post")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private KomentarService komentarService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> savePost(@RequestBody PostDTO postDTO, HttpServletRequest httpServletRequest) {
        try {
            Post post = new Post();
            List<Post> posts = postService.findAll();

            post.setLink(postDTO.getLink());
            post.setSlika(postDTO.getSlika());
            post.setTekst(postDTO.getTekst());

            List<Korisnik> korisniks = korisnikService.findAll();
            Korisnik korisnik = null;
            if(korisniks != null)
            {
                for(Korisnik k : korisniks){
                    if(k.getKorisnickoIme().equals(postDTO.getObjavio())){
                        korisnik = k;
                    }
                }
            }
            if(korisnik != null)
            {
                post.setObjavio(korisnik);
            }else
            {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
            postService.save(post);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

    }
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Post>> getPostovi() {
        List<Post> posts = postService.findAll();
        if(posts != null)
        {
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }
        return new ResponseEntity<>(posts, HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/public", produces = "application/json")
    public ResponseEntity<List<Post>> getPostPublic() {
        List<Post> posts = postService.findAll();
        List<Post> postsPublic = new ArrayList<>();
        if(posts != null)
        {
            for(Post p : posts){
                if(p.getObjavio().isJavan()){
                    postsPublic.add(p);
                }
            }
            return new ResponseEntity<>(postsPublic, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/{korisnickoIme}/zapraceniPostovi", produces = "application/json")
    public ResponseEntity<List<Post>> getPostFollowed(@PathVariable String korisnickoIme) {
        List<Korisnik> korisniks = korisnikService.findAll();
        List<Post> posts = postService.findAll();
        Korisnik korisnik = null;
        List<Post> postsFollowed = new ArrayList<>();
        if(korisniks != null)
        {
            for(Korisnik k : korisniks)
            {
                if(k.getKorisnickoIme().equals(korisnickoIme))
                {
                    korisnik = k;
                }
            }
            if(korisnik != null)
            {
                for(Post p : posts)
                {
                    for(Korisnik k : korisnik.getKogaPratim())
                    {
                        if(p.getObjavio() == k)
                        {
                            postsFollowed.add(p);
                        }
                    }
                }
            }

            return new ResponseEntity<>(postsFollowed, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
    }

    @PutMapping(value = "/like/{idPosta}/{korisnickoIme}",consumes = "application/json")
    public ResponseEntity<Post> lajk(@PathVariable Integer idPosta, @PathVariable String korisnickoIme, HttpServletRequest httpServletRequest) {
        try {
            List<Korisnik> korisniks = korisnikService.findAll();
            List<Post> posts = postService.findAll();
            Korisnik korisnik = null;
            Post post = null;
            for(Korisnik k : korisniks)
            {
                if(k.getKorisnickoIme().equals(korisnickoIme))
                {
                    korisnik = k;
                }
            }
            if(korisnik != null)
            {
                for(Post p : posts)
                {
                    if(p.getId() == idPosta)
                    {
                        post = p;
                    }
                }
                if(post != null)
                {
                    for(Korisnik k : post.getLajkovali())
                    {
                        if(k == korisnik)
                        {
                            return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
                        }
                    }
                    post.getLajkovali().add(korisnik);
                    post.setLajkovali(post.getLajkovali());
                    postService.save(post);
                    return new ResponseEntity<>(post, HttpStatus.CREATED);
                }
                else
                {
                    return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
                }
            }
            else
            {
                return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
            }
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping(value = "/dislike/{idPosta}/{korisnickoIme}",consumes = "application/json")
    public ResponseEntity<Post> dislajk(@PathVariable Integer idPosta, @PathVariable String korisnickoIme, HttpServletRequest httpServletRequest) {
        try {
            List<Korisnik> korisniks = korisnikService.findAll();
            List<Post> posts = postService.findAll();
            Korisnik korisnik = null;
            Post post = null;
            for(Korisnik k : korisniks)
            {
                if(k.getKorisnickoIme().equals(korisnickoIme))
                {
                    korisnik = k;
                }
            }
            if(korisnik != null)
            {
                for(Post p : posts)
                {
                    if(p.getId() == idPosta)
                    {
                        post = p;
                    }
                }
                if(post != null)
                {
                    for(Korisnik k : post.getLajkovali())
                    {
                        if(k == korisnik)
                        {
                            post.getLajkovali().remove(korisnik);
                            post.setLajkovali(post.getLajkovali());
                            postService.save(post);
                            return new ResponseEntity<>(post, HttpStatus.CREATED);
                        }
                    }
                    return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
                }
                else
                {
                    return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
                }
            }
            else
            {
                return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
            }
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping(value = "/comment/{idPosta}/{korisnickoIme}",consumes = "application/json")
    public ResponseEntity<Post> komentarisanje(@PathVariable Integer idPosta, @PathVariable String korisnickoIme, @RequestBody KomentarDTO komentarDTO, HttpServletRequest httpServletRequest) {
        try {
            List<Korisnik> korisniks = korisnikService.findAll();
            List<Post> posts = postService.findAll();
            Korisnik korisnik = null;
            Post post = null;
            for(Korisnik k : korisniks)
            {
                if(k.getKorisnickoIme().equals(korisnickoIme))
                {
                    korisnik = k;
                }
            }
            if(korisnik != null)
            {
                for(Post p : posts)
                {
                    if(p.getId() == idPosta)
                    {
                        post = p;
                    }
                }
                if(post != null)
                {
                    Komentar komentar = new Komentar();
                    komentar.setTekst(komentarDTO.getTekst());
                    komentar.setPostId(post);
                    komentar.setKorisnikId(korisnik);

                    komentarService.save(komentar);

                    post.getKomentari().add(komentar);
                    post.setKomentari(post.getKomentari());
                    postService.save(post);
                    return new ResponseEntity<>(post, HttpStatus.CREATED);
                }
                else
                {
                    return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
                }
            }
            else
            {
                return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
            }
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping(value= "/picture/{idPosta}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> updatePicture(@PathVariable Integer idPosta, @RequestParam MultipartFile slikaLob) {
        try {
            List<Post> posts = postService.findAll();
            for(Post p : posts)
            {
                if(p.getId() == idPosta)
                {
                    p.setSlikaLob(slikaLob.getBytes());
                    return new ResponseEntity<>(p, HttpStatus.CREATED);
                }
            }
            return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
        }
    }
}

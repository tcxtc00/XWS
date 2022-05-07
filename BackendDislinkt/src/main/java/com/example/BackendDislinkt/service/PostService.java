package com.example.BackendDislinkt.service;

import com.example.BackendDislinkt.model.Post;
import com.example.BackendDislinkt.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    public Post save(Post post) {
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll(); }

    public void remove(Integer id) {
        postRepository.deleteById(id);
    }
}

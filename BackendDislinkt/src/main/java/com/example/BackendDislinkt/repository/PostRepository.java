package com.example.BackendDislinkt.repository;

import com.example.BackendDislinkt.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAll();
    Post save(Post post);
    void deleteById(Integer id);
}

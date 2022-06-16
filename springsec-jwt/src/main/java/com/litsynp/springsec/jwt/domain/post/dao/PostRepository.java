package com.litsynp.springsec.jwt.domain.post.dao;

import com.litsynp.springsec.jwt.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}

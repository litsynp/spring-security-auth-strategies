package com.litsynp.springsec.oauth.domain.post.dao;

import com.litsynp.springsec.oauth.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}

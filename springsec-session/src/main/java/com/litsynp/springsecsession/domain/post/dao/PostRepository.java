package com.litsynp.springsecsession.domain.post.dao;

import com.litsynp.springsecsession.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}

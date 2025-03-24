package com.chaoticteam.backend.commentaries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chaoticteam.backend.commentaries.entities.CommentaryEntity;

@Repository
public interface CommentariesRepository extends JpaRepository<CommentaryEntity,Long> {

}

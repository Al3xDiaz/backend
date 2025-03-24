package com.chaoticteam.backend.commentaries.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chaoticteam.backend.commentaries.entities.CommentaryEntity;
import com.chaoticteam.backend.commentaries.repository.CommentariesRepository;

@Service
public class CommentariesService {

    @Autowired
    private CommentariesRepository repository;

    public List<CommentaryEntity> list(String site){
        return repository.findBySiteUrl(site);
    }

}

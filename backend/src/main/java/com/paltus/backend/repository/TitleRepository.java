package com.paltus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paltus.backend.model.Title;

public interface TitleRepository extends JpaRepository<Title, Integer> {
    
}

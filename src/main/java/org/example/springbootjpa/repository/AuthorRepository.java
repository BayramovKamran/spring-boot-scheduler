package org.example.springbootjpa.repository;

import org.example.springbootjpa.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}

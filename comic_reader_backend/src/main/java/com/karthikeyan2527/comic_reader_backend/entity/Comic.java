package com.karthikeyan2527.comic_reader_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    String name;

    String author;

    String artist;

    Integer yearOfRelease;

    String language;

    @Column(columnDefinition = "text")
    String description;

    Integer chapterCount;

    @OneToMany(mappedBy = "comic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    List<Chapter> chapters = new ArrayList<>();
}

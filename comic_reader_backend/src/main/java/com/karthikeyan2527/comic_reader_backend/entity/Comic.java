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
    private Integer id;

    private String name;

    private String author;

    private String artist;

    private Integer yearOfRelease;

    private String language;

    @Column(columnDefinition = "text")
    private String description;

    private Integer chapterCount;

    @OneToMany(mappedBy = "comic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Chapter> chapters = new ArrayList<>();
}
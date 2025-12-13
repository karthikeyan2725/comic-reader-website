package com.karthikeyan2527.comic_reader_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // TODO: Add Integer Primary Key

    @Id
    private String email;

    private String password;
}
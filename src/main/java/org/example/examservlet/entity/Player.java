package org.example.examservlet.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private int playerId;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "full_name", nullable = false, length = 128)
    private String fullName;

    @Column(name = "age", nullable = false, length = 10)
    private int age;

    @ManyToOne
    @JoinColumn(name = "index_id", nullable = false)
    private Indexer indexer;
}

package org.example.examservlet.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "indexer")
public class Indexer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "index_id")
    private Integer indexId;
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    @Column(name = "valueMin", nullable = false)
    private Float valueMin;
    @Column(name = "valueMax", nullable = false)
    private Float valueMax;
}

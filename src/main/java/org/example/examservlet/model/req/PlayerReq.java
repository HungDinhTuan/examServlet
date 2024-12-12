package org.example.examservlet.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerReq {
    private int playerId;
    private String fullName;
    private int age;
    private String indexName;
    private float value;
}

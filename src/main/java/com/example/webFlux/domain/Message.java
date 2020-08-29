package com.example.webFlux.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    private Long id;

    private String data;

    public Message(String s) {
    }
}

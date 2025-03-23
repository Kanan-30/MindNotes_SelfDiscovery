package org.example.self_discovery_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_responses")
public class UserResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dummyId;

    @Column(nullable = false)
    private Long questionId;

    @Column(nullable = false)
    private String selectedOption;

    // Constructors, Getters, and Setters
    public UserResponse() {
    }



//    public UserResponse(String dummyId, Question question, String selectedOption) {
//        this.dummyId = dummyId;
//        this.question = question;
//        this.selectedOption = selectedOption;
//    }
}



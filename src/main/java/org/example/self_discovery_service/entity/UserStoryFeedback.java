package org.example.self_discovery_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "user_story_feedback")
public class UserStoryFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dummyId;

    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    @Column(nullable = false)
    private String similarityLevel;

    // Constructors, Getters, and Setters
    public UserStoryFeedback() {
    }

    public UserStoryFeedback(String dummyId, Story story, String similarityLevel) {
        this.dummyId = dummyId;
        this.story = story;
        this.similarityLevel = similarityLevel;
    }
}
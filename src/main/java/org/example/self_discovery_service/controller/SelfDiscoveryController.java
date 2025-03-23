package org.example.self_discovery_service.controller;

import org.example.self_discovery_service.entity.Option;
import org.example.self_discovery_service.entity.Question;
import org.example.self_discovery_service.entity.Report;
import org.example.self_discovery_service.entity.UserResponse;
import org.example.self_discovery_service.repository.OptionRepository;
import org.example.self_discovery_service.repository.QuestionRepository;
import org.example.self_discovery_service.repository.ReportRepository;
import org.example.self_discovery_service.repository.UserResponseRepository;
import org.example.self_discovery_service.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/self-discovery")
public class SelfDiscoveryController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private UserResponseRepository userResponseRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private void validateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix

        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Invalid JWT Token");
        }
    }

    // Get Questions for a Step (JWT Protected)
    @GetMapping("/questions/{step}")
    public List<Question> getQuestions(@RequestHeader("Authorization") String authHeader, @PathVariable String step) {
        validateToken(authHeader);
        return questionRepository.findByStep(step);
    }

    // Get Options for a Question (JWT Protected)
    @GetMapping("/options/{questionId}")
    public List<Option> getOptions(@RequestHeader("Authorization") String authHeader, @PathVariable Long questionId) {
        validateToken(authHeader);
        return optionRepository.findByQuestionId(questionId);
    }

    // Save User Response (JWT Protected)
    @PostMapping("/user-response")
    public String saveUserResponse(@RequestHeader("Authorization") String authHeader, @RequestBody UserResponse userResponse) {
        validateToken(authHeader);

        if (userResponse.getQuestionId() == null) {
            return "{\"error\": \"Question ID is required!\"}";
        }

        userResponseRepository.save(userResponse);
        return "{\"message\": \"Response saved successfully!\"}";
    }

    // Get User Report (JWT Protected)
    @GetMapping("/report/{dummyId}")
    public List<Report> getReport(@RequestHeader("Authorization") String authHeader, @PathVariable String dummyId) {
        validateToken(authHeader);

        // Fetch all responses for the given dummyId
        List<UserResponse> userResponses = userResponseRepository.findByDummyId(dummyId);

        // Fetch reports based on the user's responses
        List<Report> reports = new ArrayList<>();
        for (UserResponse response : userResponses) {
            Optional<Report> report = reportRepository.findByQuestionIdAndSelectedOption(
                    response.getQuestionId(),
                    response.getSelectedOption()
            );
            report.ifPresent(reports::add);
        }

        return reports;
    }
}

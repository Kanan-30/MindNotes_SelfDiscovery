package org.example.self_discovery_service.controller;


import org.example.self_discovery_service.entity.Option;
import org.example.self_discovery_service.entity.Question;
import org.example.self_discovery_service.entity.Report;
import org.example.self_discovery_service.entity.UserResponse;
import org.example.self_discovery_service.repository.OptionRepository;
import org.example.self_discovery_service.repository.QuestionRepository;
import org.example.self_discovery_service.repository.ReportRepository;
import org.example.self_discovery_service.repository.UserResponseRepository;
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

    // Get Questions for a Step
    @GetMapping("/questions/{step}")
    public List<Question> getQuestions(@PathVariable String step) {
        return questionRepository.findByStep(step);

    }

    // Get Options for a Question
    @GetMapping("/options/{questionId}")
    public List<Option> getOptions(@PathVariable Long questionId) {
        return optionRepository.findByQuestionId(questionId);
    }

    @PostMapping("/user-response")
    public String saveUserResponse(@RequestBody UserResponse userResponse) {
        if (userResponse.getQuestionId() == null) {
            return "{\"error\": \"Question ID is required!\"}";
        }

        userResponseRepository.save(userResponse);
        return "{\"message\": \"Response saved successfully!\"}";
    }



    @GetMapping("/report/{dummyId}")
    public List<Report> getReport(@PathVariable String dummyId) {
        // Fetch all responses for the given dummyId
        List<UserResponse> userResponses = userResponseRepository.findByDummyId(dummyId);

        // Fetch reports based on the user's responses
        List<Report> reports = new ArrayList<>();
        for (UserResponse response : userResponses) {
            Optional<Report> report = reportRepository.findByQuestionIdAndSelectedOption(
                    response.getQuestionId(),  // Directly use questionId
                    response.getSelectedOption()
            );
            report.ifPresent(reports::add);
        }

        return reports;
    }


}

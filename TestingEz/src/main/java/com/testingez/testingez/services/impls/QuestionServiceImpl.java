package com.testingez.testingez.services.impls;

import com.testingez.testingez.models.dtos.exp.QuestionAnswerDTO;
import com.testingez.testingez.models.dtos.imp.QuestionCreateDTO;
import com.testingez.testingez.models.dtos.imp.TestQuestionsDTO;
import com.testingez.testingez.models.entities.Question;
import com.testingez.testingez.models.entities.Test;
import com.testingez.testingez.repositories.QuestionRepository;
import com.testingez.testingez.repositories.TestRepository;
import com.testingez.testingez.services.QuestionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean putDown(TestQuestionsDTO testQuestionsDTO) {
        List<QuestionCreateDTO> questions = testQuestionsDTO.getQuestions();
        Test lastAddedTest = this.testRepository.findLastAdded()
                .orElseThrow(() -> new NullPointerException("The last added test was not found!"));
        for (int i = 0; i < questions.size(); i++) {
            Question question = this.modelMapper.map(questions.get(i), Question.class);
            question.setTest(lastAddedTest);
            question.setNumber(i + 1);
            this.questionRepository.saveAndFlush(question);
        }
        return true;
    }

    @Override
    public int getQuestionsCountOfTheLastAddedTest() {
        return this.testRepository
                .findLastAdded()
                .orElseThrow(() ->
                        new NullPointerException("The last added test was not found!"))
                .getQuestionsCount();
    }

    @Override
    public QuestionAnswerDTO fetchQuestionData(Long testId, Integer questionNumber) {
        Test test = this.testRepository.findById(testId)
                .orElseThrow(() -> new NullPointerException("Test with id: " + testId + " was not found!"));
        if (test.getQuestionsCount() < questionNumber) {
            return null;
        }
        Question question = this.questionRepository.findByTestIdAndNumber(testId, questionNumber)
                        .orElseThrow(() -> new NullPointerException("Question(#" + questionNumber + ") associated with test: " + testId +
                                "was not found!"));
        QuestionAnswerDTO map = this.modelMapper.map(question, QuestionAnswerDTO.class);
        map.setResponseTime(test.getResponseTime());
        map.setTestId(testId);
        return map;
    }

}

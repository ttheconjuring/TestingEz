package com.testingez.testingez.services.impls;

import com.testingez.testingez.exceptions.custom.QuestionNotFoundException;
import com.testingez.testingez.exceptions.custom.ResultNotFoundException;
import com.testingez.testingez.exceptions.custom.TestNotFoundException;
import com.testingez.testingez.models.dtos.exp.AnsweredQuestionDTO;
import com.testingez.testingez.models.dtos.exp.QuestionAnswerDTO;
import com.testingez.testingez.models.dtos.imp.QuestionCreateDTO;
import com.testingez.testingez.models.dtos.imp.TestQuestionsDTO;
import com.testingez.testingez.models.entities.Question;
import com.testingez.testingez.models.entities.Result;
import com.testingez.testingez.models.entities.Test;
import com.testingez.testingez.repositories.QuestionRepository;
import com.testingez.testingez.repositories.ResultRepository;
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
    private final ResultRepository resultRepository;

    @Override
    public void putDown(TestQuestionsDTO testQuestionsDTO, Long testId) {
        List<QuestionCreateDTO> questions = testQuestionsDTO.getQuestions();
        Test lastAddedTest = this.testRepository.findById(testId)
                .orElseThrow(() -> new TestNotFoundException("We couldn't test with id: " + testId + "!"));
        for (int i = 0; i < questions.size(); i++) {
            Question question = this.modelMapper.map(questions.get(i), Question.class);
            question.setTest(lastAddedTest);
            question.setNumber(i + 1);
            this.questionRepository.saveAndFlush(question);
        }
    }

    @Override
    public Integer getQuestionsCountOfTheTest(Long testId) {
        return this.testRepository
                .findById(testId)
                .orElseThrow(() ->
                        new TestNotFoundException("We couldn't test with id: " + testId + "!"))
                .getQuestionsCount();
    }

    @Override
    public QuestionAnswerDTO fetchQuestionData(Long testId, Integer questionNumber) {
        Test test = this.testRepository.findById(testId)
                .orElseThrow(() -> new TestNotFoundException("We couldn't find test with id: " + testId + "!"));
        if (test.getQuestionsCount() < questionNumber) {
            return null;
        }
        Question question = this.questionRepository.findByTestIdAndNumber(testId, questionNumber)
                .orElseThrow(() -> new QuestionNotFoundException("Question(№" + questionNumber + ") associated with test: " + testId +
                        "was not found!"));
        QuestionAnswerDTO map = this.modelMapper.map(question, QuestionAnswerDTO.class);
        map.setResponseTime(test.getResponseTime());
        map.setTestId(testId);
        return map;
    }

    @Override
    public List<AnsweredQuestionDTO> getAnsweredQuestionsData(Long resultId) {
        Result result = this.resultRepository.findById(resultId)
                .orElseThrow(() -> new ResultNotFoundException("We couldn't find result with id: " + resultId + "!"));
        Long testId = result.getTest().getId();
        return this.questionRepository.findAllByTestId(testId)
                .stream()
                .map(question -> this.modelMapper.map(question, AnsweredQuestionDTO.class))
                .toList();
    }

}

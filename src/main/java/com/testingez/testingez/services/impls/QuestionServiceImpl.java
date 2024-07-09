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
import java.util.Optional;

@AllArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    @Override
    public boolean putDown(TestQuestionsDTO testQuestionsDTO) {
        List<QuestionCreateDTO> questions = testQuestionsDTO.getQuestions();
        Optional<Test> lastAddedTest = this.testRepository.findLastAdded();
        if (lastAddedTest.isEmpty()) {
            return false;
        }
        for (int i = 0; i < questions.size(); i++) {
            Question question = this.modelMapper.map(questions.get(i), Question.class);
            question.setTest(lastAddedTest.get());
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
        Optional<Question> byTestIdAndNumber = this.questionRepository.findByTestIdAndNumber(testId, questionNumber);
        if (byTestIdAndNumber.isEmpty()) {
            return null;
        }
        QuestionAnswerDTO map = this.modelMapper.map(byTestIdAndNumber.get(), QuestionAnswerDTO.class);
        map.setResponseTime(this.testRepository.findById(testId).get().getResponseTime());
        return map;
    }

}

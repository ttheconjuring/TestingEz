package com.testingez.testingez.services.impls;

import com.testingez.testingez.exceptions.custom.QuestionNotFoundException;
import com.testingez.testingez.exceptions.custom.ResultNotFoundException;
import com.testingez.testingez.exceptions.custom.TestNotFoundException;
import com.testingez.testingez.models.dtos.exp.AnsweredQuestionDTO;
import com.testingez.testingez.models.dtos.exp.QuestionAnswerDTO;
import com.testingez.testingez.models.dtos.exp.QuestionDetailsDTO;
import com.testingez.testingez.models.dtos.exp.ResponseToQuestionDTO;
import com.testingez.testingez.models.dtos.imp.QuestionCreateDTO;
import com.testingez.testingez.models.dtos.imp.QuestionEditDTO;
import com.testingez.testingez.models.dtos.imp.TestQuestionsDTO;
import com.testingez.testingez.models.entities.Question;
import com.testingez.testingez.models.entities.Result;
import com.testingez.testingez.models.entities.Test;
import com.testingez.testingez.repositories.QuestionRepository;
import com.testingez.testingez.repositories.ResponseRepository;
import com.testingez.testingez.repositories.ResultRepository;
import com.testingez.testingez.repositories.TestRepository;
import com.testingez.testingez.services.QuestionService;
import com.testingez.testingez.services.ResultService;
import com.testingez.testingez.services.UserHelperService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final ModelMapper modelMapper;
    private final ResultRepository resultRepository;
    private final ResponseRepository responseRepository;
    private final UserHelperService userHelperService;
    private final ResultService resultService;

    /*
     * This method is invoked when the user clicks on the complete button
     * located in the question creation page. The method waits for object
     * and id. The object is holding question object and the id is pointing
     * to the corresponding test. If the test is not found, an error is
     * thrown. Otherwise, the list of question object is iterated and each
     * object is mapped to question entity. Then the test is attached to each
     * question and also a number is set. Then the question is saved in the
     * database and the process repeats for all question objects.
     */
    @Override
    public void putDown(TestQuestionsDTO testQuestionsDTO, Long testId) {
        List<QuestionCreateDTO> questions = testQuestionsDTO.getQuestions();
        Test test = this.testRepository.findById(testId)
                .orElseThrow(() -> new TestNotFoundException("We couldn't find test with id: " + testId));
        for (int i = 0; i < questions.size(); i++) {
            Question question = this.modelMapper.map(questions.get(i), Question.class);
            question.setTest(test);
            question.setNumber(i + 1);
            this.questionRepository.saveAndFlush(question);
        }
    }

    /*
     * This method returns the number of question a test has. It accepts
     * test id, finds the test id and simply returns the field called
     * 'questionsCount'. If the test is not found, an error is thrown.
     */
    @Override
    public Integer getQuestionsCountOfTheTest(Long testId) {
        return this.testRepository
                .findById(testId)
                .orElseThrow(() ->
                        new TestNotFoundException("We couldn't find test with id: " + testId))
                .getQuestionsCount();
    }

    /*
     * This method returns question data needed for a user to answer a question. This
     * method is invoked before the user proceeds to the next question, so it prepares
     * the template. The methods receives test id and question number, so it can find
     * the exact question. If the test is not found, then error is thrown. In case when
     * the question number is bigger than the question count, then the test is considered
     * over and null is returned since there is no more questions left in this test to answer.
     * Otherwise, the question found is mapped to DTO and returned. The DTO is coming with
     * the time for response and also the test id.
     */
    @Override
    public QuestionAnswerDTO fetchQuestionData(Long testId, Integer questionNumber) {
        Test test = this.testRepository.findById(testId)
                .orElseThrow(() -> new TestNotFoundException("We couldn't find test with id: " + testId));
        if (test.getQuestionsCount() < questionNumber) {
            this.resultService.calculateResult(testId,
                    this.userHelperService.getLoggedUser().getId());
            return null;
        }
        Question question = this.questionRepository.findByTestIdAndNumber(testId, questionNumber)
                .orElseThrow(() -> new QuestionNotFoundException("Question №" + questionNumber + " associated with test: " + testId +
                        " was not found!"));
        QuestionAnswerDTO map = this.modelMapper.map(question, QuestionAnswerDTO.class);
        map.setResponseTime(test.getResponseTime());
        map.setTestId(testId);
        return map;
    }

    /*
     * This method is invoked when the user wants to see details on how he has performed.
     * It accepts the result id and using it, we first finds the test. Then we extract the
     * questions of the test along with the correct answers and also the given answers, so
     * the user can compare them. Then we return the list, holding such question objects.
     */
    @Override
    public List<AnsweredQuestionDTO> getAnsweredQuestionsData(Long resultId) {
        Result result = this.resultRepository.findById(resultId)
                .orElseThrow(() -> new ResultNotFoundException("We couldn't find result with id: " + resultId));
        Long testId = result.getTest().getId();
        List<ResponseToQuestionDTO> responses = this.responseRepository.findAllByTestIdAndUserId(testId, this.userHelperService.getLoggedUser().getId())
                .stream()
                .map(response -> this.modelMapper.map(response, ResponseToQuestionDTO.class))
                .toList();
        List<AnsweredQuestionDTO> questions = this.questionRepository.findAllByTestId(testId)
                .stream()
                .map(question -> this.modelMapper.map(question, AnsweredQuestionDTO.class))
                .toList();
        if (responses.size() != questions.size()) {
            throw new ArrayStoreException("Responses count doesn't match the questions count!");
        }
        int n = responses.size();
        for (int i = 0; i < n; i++) {
            questions.get(i).setGivenAnswer(responses.get(i).getResponseText());
        }
        return questions;
    }

    /*
     * This method gets the questions of a test. It receives id
     * and tries to find the test. When the test is found, each
     * question is mapped to DTO and at the end, a list is returned
     * with the mapped objects. This method is invoked when the user
     * tries to see the result on the test. This method simply returns
     * the questions of the test.
     */
    @Override
    public List<QuestionDetailsDTO> getQuestionsOfATest(Long testId) {
        return this.questionRepository.findAllByTestId(testId)
                .stream()
                .map(question ->
                        this.modelMapper.map(question, QuestionDetailsDTO.class)
                ).toList();
    }

    /*
     * This method is invoked when a user tries to edit given question.
     * The method accepts question id and tries to find it. If the
     * question is not found, an error is thrown. If it is found,
     * it is mapped to dto, so the user can edit only the question
     * and the answers and then returned. It doesn't edit the question,
     * just finds it and returns it.
     */
    @Override
    public QuestionEditDTO fetchQuestionData(Long questionId) {
        return this.modelMapper.map(
                this.questionRepository.findById(questionId)
                        .orElseThrow(
                                () -> new QuestionNotFoundException("We couldn't find question with id: " + questionId)
                        ), QuestionEditDTO.class
        );
    }

    /*
     * This method is invoked when the user has made changes to their question.
     * The method waits for an object holding the updated question. The question
     * will be edited only if the test is not attended yet. It tries to
     * find the question first, and if it is not found, an error is thrown. If the question
     * is found, then all the data from the argument object is mirrored to the
     * question entity. The question is freshly updated then.
     */
    @Override
    public Boolean edit(QuestionEditDTO questionEditDTO) {
        if (this.resultRepository.countByTestId(questionEditDTO.getTestId()) > 0) {
            return false;
        }
        Question question = this.questionRepository.findById(questionEditDTO.getId())
                .orElseThrow(() -> new QuestionNotFoundException("We couldn't find question with id: " + questionEditDTO.getId() + "!"));
        question.setQuestion(questionEditDTO.getQuestion());
        question.setAnswer1(questionEditDTO.getAnswer1());
        question.setAnswer2(questionEditDTO.getAnswer2());
        question.setAnswer3(questionEditDTO.getAnswer3());
        question.setAnswer4(questionEditDTO.getAnswer4());
        question.setCorrectAnswer(questionEditDTO.getCorrectAnswer());
        this.questionRepository.saveAndFlush(question);
        return true;
    }

    /*
     * This method adds a new question to the database and associates the test to it.
     * It will be added to the test only if the test is not attended yet. If the test is
     * not found, then an error is thrown. Otherwise, we get the questions counts in order to
     * set number to the new question and also to increase the value by one. After the question
     * is created, the number is set and also the test. Then we update the question count of
     * the test and save the test and the question both.
     */
    @Override
    public Boolean add(Long testId, QuestionCreateDTO questionData) {
        if (this.resultRepository.countByTestId(testId) > 0) {
            return false;
        }
        Test test = this.testRepository.findById(testId)
                .orElseThrow(() -> new TestNotFoundException("We couldn't find test with id: " + testId));
        Integer questionsCount = test.getQuestionsCount();
        Question question = this.modelMapper.map(questionData, Question.class);
        question.setNumber(questionsCount + 1);
        question.setTest(test);
        test.setQuestionsCount(questionsCount + 1);
        this.questionRepository.saveAndFlush(question);
        this.testRepository.saveAndFlush(test);
        return true;
    }

    /*
     * This method waits for question id and test id, then checks if the test is attended.
     * In case the test is attended, then it returns false. If the test it not attended,
     * it tries to find the test. If the test is not found, an error is thrown. Otherwise,
     * the test question count is decreased by one and lastly the question is deleted. True
     * is returned.
     */
    @Override
    public Boolean delete(Long questionId, Long testId) {
        if (this.resultRepository.countByTestId(testId) > 0) {
            return false;
        }
        Test test = this.testRepository.findById(testId)
                .orElseThrow(() -> new TestNotFoundException("We couldn't find test with id: " + testId));
        if (test.getQuestionsCount() <= 1) {
            return false;
        }
        test.setQuestionsCount(test.getQuestionsCount() - 1);
        this.questionRepository.deleteById(questionId);
        touchNumbering(testId);
        return true;
    }

    private void touchNumbering(Long testId) {
        List<Question> questions = this.questionRepository.findAllByTestId(testId)
                .stream().sorted(Comparator.comparing(Question::getNumber)).toList();
        int n = questions.size();
        for (int i = 1; i <= n; i++) {
            Question question = questions.get(i - 1);
            if (question.getNumber() != i) {
                question.setNumber(i);
                this.questionRepository.saveAndFlush(question);
            }
        }
    }

}

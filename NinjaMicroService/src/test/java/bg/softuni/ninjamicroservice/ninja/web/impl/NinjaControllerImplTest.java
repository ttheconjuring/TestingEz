package bg.softuni.ninjamicroservice.ninja.web.impl;

import bg.softuni.ninjamicroservice.ninja.dtos.*;
import bg.softuni.ninjamicroservice.ninja.service.NinjaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class NinjaControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NinjaService ninjaService;

    @Test
    void getFactsShouldReturnFacts() throws Exception {
        // given
        FactDTO fact1 = new FactDTO();
        fact1.setFact("fact 1");
        FactDTO fact2 = new FactDTO();
        fact2.setFact("fact 2");
        FactDTO fact3 = new FactDTO();
        fact3.setFact("fact 3");
        FactDTO[] facts = {fact1, fact2, fact3};
        when(ninjaService.getFacts()).thenReturn(facts);

        // when
        mockMvc.perform(get("/ninja/api/facts"))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].fact", is("fact 1")))
                .andExpect(jsonPath("$[1].fact", is("fact 2")))
                .andExpect(jsonPath("$[2].fact", is("fact 3")));
    }

    @Test
    void getJokesShouldReturnJokes() throws Exception {
        // given
        JokeDTO joke1 = new JokeDTO();
        joke1.setJoke("joke 1");
        JokeDTO joke2 = new JokeDTO();
        joke2.setJoke("joke 2");
        JokeDTO joke3 = new JokeDTO();
        joke3.setJoke("joke 3");
        JokeDTO[] jokes = {joke1, joke2, joke3};
        when(ninjaService.getJokes()).thenReturn(jokes);

        // when
        mockMvc.perform(get("/ninja/api/jokes"))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].joke", is("joke 1")))
                .andExpect(jsonPath("$[1].joke", is("joke 2")))
                .andExpect(jsonPath("$[2].joke", is("joke 3")));
    }

    @Test
    void getTriviaShouldReturnTrivia() throws Exception {
        // given
        TriviaDTO trivia1 = new TriviaDTO();
        trivia1.setCategory("cat1");
        trivia1.setQuestion("q1?");
        trivia1.setAnswer("a1");
        TriviaDTO trivia2 = new TriviaDTO();
        trivia2.setCategory("cat2");
        trivia2.setQuestion("q2?");
        trivia2.setAnswer("a2");
        TriviaDTO trivia3 = new TriviaDTO();
        trivia3.setCategory("cat3");
        trivia3.setQuestion("q3?");
        trivia3.setAnswer("a3");
        TriviaDTO[] trivias = {trivia1, trivia2, trivia3};
        when(ninjaService.getTrivia()).thenReturn(trivias);

        // when
        mockMvc.perform(get("/ninja/api/trivia"))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].category", is("cat1")))
                .andExpect(jsonPath("$[0].question", is("q1?")))
                .andExpect(jsonPath("$[0].answer", is("a1")))
                .andExpect(jsonPath("$[1].category", is("cat2")))
                .andExpect(jsonPath("$[1].question", is("q2?")))
                .andExpect(jsonPath("$[1].answer", is("a2")))
                .andExpect(jsonPath("$[2].category", is("cat3")))
                .andExpect(jsonPath("$[2].question", is("q3?")))
                .andExpect(jsonPath("$[2].answer", is("a3")));
    }

    @Test
    void getQuotesShouldReturnQuotes() throws Exception {
        // given
        QuoteDTO quote1 = new QuoteDTO();
        quote1.setQuote("q1");
        quote1.setAuthor("a1");
        QuoteDTO quote2 = new QuoteDTO();
        quote2.setQuote("q2");
        quote2.setAuthor("a2");
        QuoteDTO quote3 = new QuoteDTO();
        quote3.setQuote("q3");
        quote3.setAuthor("a3");
        QuoteDTO[] quotes = {quote1, quote2, quote3};
        when(ninjaService.getQuotes()).thenReturn(quotes);

        // when
        mockMvc.perform(get("/ninja/api/quotes"))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].quote", is("q1")))
                .andExpect(jsonPath("$[0].author", is("a1")))
                .andExpect(jsonPath("$[1].quote", is("q2")))
                .andExpect(jsonPath("$[1].author", is("a2")))
                .andExpect(jsonPath("$[2].quote", is("q3")))
                .andExpect(jsonPath("$[2].author", is("a3")));
    }

    @Test
    void getImprovementsShouldReturnImprovements() throws Exception {
        // given
        ImprovementDTO improvement1 = new ImprovementDTO();
        improvement1.setId(UUID.randomUUID());
        improvement1.setIdea("idea1");
        improvement1.setApproved(false);
        improvement1.setDisapproved(false);
        ImprovementDTO improvement2 = new ImprovementDTO();
        improvement2.setId(UUID.randomUUID());
        improvement2.setIdea("idea2");
        improvement2.setApproved(true);
        improvement2.setDisapproved(false);
        ImprovementDTO improvement3 = new ImprovementDTO();
        improvement3.setId(UUID.randomUUID());
        improvement3.setIdea("idea3");
        improvement3.setApproved(false);
        improvement3.setDisapproved(true);
        ImprovementDTO[] improvements = {improvement1, improvement2, improvement3};
        when(ninjaService.getImprovements()).thenReturn(improvements);

        // when
        mockMvc.perform(get("/ninja/api/improvements"))

                // then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(notNullValue())))
                .andExpect(jsonPath("$[0].idea", is("idea1")))
                .andExpect(jsonPath("$[0].approved", is(false)))
                .andExpect(jsonPath("$[0].disapproved", is(false)))
                .andExpect(jsonPath("$[1].id", is(notNullValue())))
                .andExpect(jsonPath("$[1].idea", is("idea2")))
                .andExpect(jsonPath("$[1].approved", is(true)))
                .andExpect(jsonPath("$[1].disapproved", is(false)))
                .andExpect(jsonPath("$[2].id", is(notNullValue())))
                .andExpect(jsonPath("$[2].idea", is("idea3")))
                .andExpect(jsonPath("$[2].approved", is(false)))
                .andExpect(jsonPath("$[2].disapproved", is(true)));
    }

    @Test
    void postImprovementShouldCreateImprovement() throws Exception {
        // given
        ImprovementDTO improvement = new ImprovementDTO();
        improvement.setId(UUID.randomUUID());
        improvement.setIdea("idea");
        improvement.setApproved(false);
        improvement.setDisapproved(false);
        when(ninjaService.postImprovement(Mockito.any(ImprovementDTO.class)))
                .thenReturn(improvement);

        // when
        mockMvc.perform(post("/ninja/api/improvements/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(improvement.toString()))

                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.idea", is("idea")))
                .andExpect(jsonPath("$.approved", is(false)))
                .andExpect(jsonPath("$.disapproved", is(false)));
    }

    @Test
    void deleteImprovementShouldDeleteImprovement() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        ImprovementDTO improvement = new ImprovementDTO();
        improvement.setId(id);
        improvement.setIdea("idea");
        improvement.setApproved(false);
        improvement.setDisapproved(true);
        when(ninjaService.deleteImprovement(id)).thenReturn(improvement);

        // when
        mockMvc.perform(delete("/ninja/api/improvements/delete/{id}", id))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.idea", is("idea")))
                .andExpect(jsonPath("$.approved", is(false)))
                .andExpect(jsonPath("$.disapproved", is(true)));
    }

    @Test
    void deleteImprovementShouldReturnNotFound() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        when(ninjaService.deleteImprovement(id)).thenReturn(null);

        // when
        mockMvc.perform(delete("/ninja/api/improvements/delete/{id}", id))

                // then
                .andExpect(status().isNotFound())
                .andExpect(content().string("We couldn't find such improvement idea!"));
    }

}
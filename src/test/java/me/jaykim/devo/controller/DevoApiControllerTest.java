package me.jaykim.devo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jaykim.devo.domain.Member;
import me.jaykim.devo.dto.AddMemberRequest;
import me.jaykim.devo.repository.DevoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class DevoApiControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    DevoRepository devoRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        devoRepository.deleteAll();
    }

    @DisplayName("addMember: 회원가입에 성공한다.")
    @Test
    public void addMember() throws Exception{
        final String url = "/api/members";
        final String userId = "userId";
        final String repoURL = "repoURL";
        final String gitToken = "gitToken";
        final AddMemberRequest userRequest = new AddMemberRequest(userId, repoURL,gitToken);

        final String requestBody = objectMapper.writeValueAsString(userRequest);

        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        result.andExpect(status().isCreated());

        List<Member> members = devoRepository.findAll();

        assertThat(members.size()).isEqualTo(1);
        assertThat(members.get(0).getUserId()).isEqualTo(userId);
        assertThat(members.get(0).getRepoURL()).isEqualTo(repoURL);
        assertThat(members.get(0).getGitToken()).isEqualTo(gitToken);


    }


    @DisplayName("findMember: 로그인에 성공한다.")
    @Test
    public void findMember() throws Exception {
        final String url = "/api/members/{userId}";
        final String userId = "userId";
        final String repoURL = "repoURL";
        final String gitToken = "gitToken";

        Member savedMember = devoRepository.save(Member.builder()
                .userId(userId)
                .repoURL(repoURL)
                .gitToken(gitToken)
                .build());

        final ResultActions resultActions = mockMvc.perform(get(url, savedMember.getUserId()));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.repoURL").value(repoURL))
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.gitToken").value(gitToken));
    }

}
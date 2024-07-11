package me.jaykim.devo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.jaykim.devo.domain.Member;

@NoArgsConstructor
@Getter
@Setter
public class LogInResponse {

    private Long id;
    private String userId;
    private String repoURL;
    private String gitToken;

    public LogInResponse(Member member) {
        this.id = member.getId();
        this.userId = member.getUserId();
        this.repoURL = member.getRepoURL();
        this.gitToken = member.getGitToken();
    }
}


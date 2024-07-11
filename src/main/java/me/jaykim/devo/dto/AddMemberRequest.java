package me.jaykim.devo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jaykim.devo.domain.Member;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddMemberRequest {

    private String userId;
    private String repoURL;
    private String gitToken;

    public Member toEntity(){
        return Member.builder()
                .userId(userId)
                .repoURL(repoURL)
                .gitToken(gitToken)
                .build();
    }
}

package me.jaykim.devo.domain;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.io.Serializable;

@Entity(name = "member")
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "repourl", nullable = false)
    private String repoURL;

    @Column(name = "git_token", nullable = false)
    private String gitToken;

    @Builder
    public Member(String userId, String repoURL, String gitToken){
        this.userId = userId;
        this.repoURL = repoURL;
        this.gitToken = gitToken;
    }
}

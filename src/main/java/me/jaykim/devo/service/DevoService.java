package me.jaykim.devo.service;

import lombok.RequiredArgsConstructor;
import me.jaykim.devo.domain.Member;
import me.jaykim.devo.dto.AddMemberRequest;
import me.jaykim.devo.repository.DevoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DevoService {
    private final DevoRepository devoRepository;

    public Member save(AddMemberRequest request) {
        return devoRepository.save(request.toEntity());
    }

    public Member findById(Long id) {
        return devoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public Member findByUserId(String userId) {
        return devoRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + userId));
    }

    public Long getIdByUserId(String userId) {
        Member member = findByUserId(userId);
        return member.getId();
    }

}

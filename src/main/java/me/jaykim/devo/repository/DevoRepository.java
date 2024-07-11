package me.jaykim.devo.repository;

import me.jaykim.devo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DevoRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);
}

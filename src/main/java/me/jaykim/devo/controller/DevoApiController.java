package me.jaykim.devo.controller;

import lombok.RequiredArgsConstructor;
import me.jaykim.devo.domain.Member;
import me.jaykim.devo.dto.AddMemberRequest;
import me.jaykim.devo.dto.LogInResponse;
import me.jaykim.devo.service.DevoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DevoApiController {

    private final DevoService devoService;

    @PostMapping("/api/members")
    public ResponseEntity<Member> addMember(@RequestBody AddMemberRequest request){
        Member savedMember = devoService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedMember);
    }

    @GetMapping("/api/members/{userId}")
    public ResponseEntity<LogInResponse> getUserById(@PathVariable String userId) {
        Member logInMember = devoService.findByUserId(userId);
        return ResponseEntity.ok()
                .body(new LogInResponse(logInMember));
    }

    @GetMapping("/api/members/{userId}/id")
    public ResponseEntity<Long> getIdByUserId(@PathVariable String userId) {
        Long id = devoService.getIdByUserId(userId);
        return ResponseEntity.ok()
                .body(id);
    }


}


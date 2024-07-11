package me.jaykim.devo.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/issues")
public class GitHubIssuesController {

    @GetMapping("/{issueNumber}")
    public ResponseEntity<Resource> getIssueFile(@PathVariable int issueNumber) throws IOException {
        // 파일 경로 생성
        String filename = "journal/issue_" + issueNumber + ".md";
        File file = new File(filename);

        // 파일이 존재하는지 확인
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 파일을 Resource로 변환
        Resource resource = new FileSystemResource(file);

        // 응답 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, "text/markdown");

        // ResponseEntity 생성 및 반환
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}


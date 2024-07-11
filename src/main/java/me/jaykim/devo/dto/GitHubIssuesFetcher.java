package me.jaykim.devo.dto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import me.jaykim.devo.domain.Member;

public class GitHubIssuesFetcher {

    private String userId;
    private String repoURL;
    private String gitToken;

    public GitHubIssuesFetcher(Member member){
        this.userId = member.getUserId();
        this.repoURL = member.getRepoURL();
        this.gitToken = member.getGitToken();
    }

    private void setGitHubToken(String token) {
        this.gitToken = token;
    }

    private void setRepoURL(String url) {
        this.repoURL = url;
    }

    public static void main(String[] args) {
        try {
            // Create a Member object for testing
            GitHubIssuesFetcher fetcher = getGitHubIssuesFetcher();

            // Fetch GitHub issues
            JSONArray issues = fetcher.fetchIssues();

            // Create journal directory
            File journalDir = new File("journal");
            if (!journalDir.exists()) {
                journalDir.mkdirs();
            }

            // Save issues to files
            for (int i = 0; i < issues.length(); i++) {
                JSONObject issue = issues.getJSONObject(i);
                int issueNumber = issue.getInt("number");
                String issueTitle = issue.getString("title");
                String issueBody = issue.getString("body");
                String issueCreatedAt = issue.getString("created_at");

                // Create filename
                String filename = "journal/issue_" + issueNumber + ".md";

                // Save issue to file
                fetcher.saveIssueToFile(filename, issueTitle, issueCreatedAt, issueBody);
            }
        } catch (IOException | JSONException e) {
            System.out.println("Failed to fetch issues: " + e.getMessage());
        }
    }

    private static @NotNull GitHubIssuesFetcher getGitHubIssuesFetcher() {
        Member member = new Member();
        member.setUserId("your_user_id");
        member.setRepoURL("https://github.com/your_repo_owner/your_repo_name");
        member.setGitToken("your_github_token_here");

        // Initialize GitHubIssuesFetcher with the Member object
        GitHubIssuesFetcher fetcher = new GitHubIssuesFetcher(member);
        fetcher.setGitHubToken(member.getGitToken());
        fetcher.setRepoURL(member.getRepoURL());
        return fetcher;
    }

    private JSONArray fetchIssues() throws IOException, JSONException {
        URL url = new URL(this.repoURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "token " + this.gitToken);
        conn.setRequestProperty("Accept", "application/vnd.github.v3+json");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            Scanner scanner = new Scanner(conn.getInputStream());
            String responseBody = scanner.useDelimiter("\\A").next();
            scanner.close();
            return new JSONArray(responseBody);
        } else {
            String errorMessage = "Failed to fetch issues: HTTP error code " + responseCode;
            Scanner scanner = new Scanner(conn.getErrorStream());
            if (scanner.hasNext()) {
                errorMessage += "\n" + scanner.useDelimiter("\\A").next();
            }
            scanner.close();
            throw new IOException(errorMessage);
        }
    }

    private void saveIssueToFile(String filename, String title, String createdAt, String body) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write("# " + title + "\n");
            fileWriter.write("### Created at: " + createdAt + "\n\n");
            fileWriter.write(body);
        }
    }

}


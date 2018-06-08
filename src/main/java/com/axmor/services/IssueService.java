package com.axmor.services;

import com.axmor.db.entityes.Issue;
import com.axmor.db.entityes.enums.Status;
import com.axmor.db.repository.AccountRepository;
import com.axmor.db.repository.CommentRepository;
import com.axmor.db.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import spark.Request;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;
    private final AccountRepository accountRepository;

    public List<Issue> getAllIssues(){
        return issueRepository.findAll();
    }

    public Issue getIssueById(Request request){
        Long id = Long.valueOf(request.params(":id"));
        return issueRepository.findById(id);
    }

    public  ServiceMessages createIssue(Request request){
        String title = request.queryParams("title");
        String description = request.queryParams("description");
        Long author_id = request.session().attribute("id_user");
        if(title.isEmpty()){
            return new ServiceMessages(StatusMessage.error, "Empty title");
        }
        if(description.isEmpty()){
            return new ServiceMessages(StatusMessage.error, "Empty description");
        }
        Issue issue = new Issue();
        issue.setTitle(title);
        issue.setDescription(description);
        issue.setStatus(Status.Created);
        issue.setCreated(new Date());
        issue.setModified(new Date());
        issue.setAccount(accountRepository.findById(author_id));

        issueRepository.save(issue);
        return new ServiceMessages(StatusMessage.ok, "");
    }

    public Issue redact(Request request) {

        Issue issue = issueRepository.findById(Long.valueOf(request.params(":id")));

        String title = request.queryParams("title");
        String description = request.queryParams("description");
        Status status = Status.fromString(request.queryParams("status"));

        issue.setTitle(title);
        issue.setDescription(description);
        issue.setStatus(status);
        issue.setModified(new Date());

        issueRepository.update(issue);

        return issue;
    }
}

package com.axmor.services;

import com.axmor.db.entityes.Account;
import com.axmor.db.entityes.Comment;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final AccountRepository accountRepository;

    public ServiceMessages createComment(Request request) {
        Long id_account = request.session().attribute("id_user");
        Long id_issue = Long.valueOf(request.params(":id"));

        Issue issue = issueRepository.findById(id_issue);
        Account account = accountRepository.findById(id_account);

        String description = request.queryParams("description");
        Status status = Status.fromString(request.queryParams("status"));

        if(description.isEmpty()){
            return new ServiceMessages(StatusMessage.error, "Empty description");
        }

        issue.setStatus(status);

        Comment comment = new Comment();
        comment.setAccount(account);
        comment.setIssue(issue);
        comment.setAccount(account);
        comment.setCreated(new Date());
        comment.setModified(new Date());
        comment.setDescription(description);
        comment.setStatus(status);

        issueRepository.update(issue);
        commentRepository.save(comment);
        return new ServiceMessages(StatusMessage.ok, "");
    }

    public List<Comment> findIssueComments(Request request){
        Long id_issue = Long.valueOf(request.params(":id"));
        return commentRepository.findByIssueId(id_issue);
    }
}

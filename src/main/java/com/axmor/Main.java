package com.axmor;

import com.axmor.db.entityes.enums.Status;
import com.axmor.db.repository.AccountRepository;
import com.axmor.db.repository.CommentRepository;
import com.axmor.db.repository.IssueRepository;
import com.axmor.services.*;
import com.axmor.util.DefaultDataInit;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

/**
 * Application entry point
 */
public class Main {

    public static void main(String[] args) {
        /**
         *  Creating default data
         */
        DefaultDataInit defaultDataInit = new DefaultDataInit();
        defaultDataInit.createDefaultData();

        AccountService accountService = new AccountService(new AccountRepository());
        IssueService issueService = new IssueService(new IssueRepository(), new AccountRepository());
        CommentService commentService = new CommentService(new CommentRepository(), new IssueRepository(), new AccountRepository());

        port(80);

        //Routing

        get("/", (request, response) ->
        {
            if (request.session().attribute("login") == null) {
                response.redirect("/login");
                return null;
            }
            Map<String, Object> model = new HashMap<>();
            model.put("issues", issueService.getAllIssues());
            model.put("user", request.session().attribute("login"));
            return render(model, "index.ftl");
        });

        get("/login", (request, response) ->
        {
            if (request.session().attribute("login") != null) {
                response.redirect("/");
            }

            return render(null, "login.ftl");
        });

        post("/login", (request, response) ->
        {
            ServiceMessages serviceMessages = accountService.autorisation(request);
            Map<String, ServiceMessages> messagesMap = new HashMap<>();
            messagesMap.put("message", serviceMessages);
            if (serviceMessages.getStatusMessage() == StatusMessage.ok) {
                response.redirect("/");
                return null;
            } else {
                return render(messagesMap, "login.ftl");
            }
        });

        get("/registration", (request, response) -> render(null, "registration.ftl"));

        get("/logout", (request, response) -> {
            accountService.logout(request);
            response.redirect("/");
            return null;
        });

        post("/registration", (request, response) ->
        {
            ServiceMessages serviceMessages = accountService.registration(request);
            Map<String, ServiceMessages> messagesMap = new HashMap<>();
            messagesMap.put("message", serviceMessages);
            if (serviceMessages.getStatusMessage() == StatusMessage.ok) {
                response.redirect("/");
                return null;
            } else {
                return render(messagesMap, "registration.ftl");
            }
        });

        get("/issue/:id", (request, response) ->
        {
            if (request.session().attribute("login") == null) {
                response.redirect("/login");
            }

            Map<String, Object> model = new HashMap<>();
            model.put("issue", issueService.getIssueById(request));
            model.put("comments", commentService.findIssueComments(request));
            model.put("user", request.session().attribute("login"));
            model.put("statuses", Status.getStatusMap());
            return render(model, "issue.ftl");
        });

        get("/create", (request, response) ->
        {
            if (request.session().attribute("login") == null) {
                response.redirect("/login");
            }

            Map<String, Object> model = new HashMap<>();
            model.put("user", request.session().attribute("login"));
            return render(model, "create.ftl");
        });

        post("/create", (request, response) ->
        {
            if (request.session().attribute("login") == null) {
                response.redirect("/login");
            }

            Map<String, Object> model = new HashMap<>();
            ServiceMessages serviceMessages = issueService.createIssue(request);
            if (serviceMessages.getStatusMessage() == StatusMessage.error) {
                model.put("message", serviceMessages);
                model.put("user", request.session().attribute("login"));
                return render(model, "create.ftl");
            }
            response.redirect("/");
            return null;
        });

        get("/issue/redact/:id", (request, response) ->
        {
            if (request.session().attribute("login") == null) {
                response.redirect("/login");
            }

            Map<String, Object> model = new HashMap<>();
            model.put("issue", issueService.getIssueById(request));
            model.put("user", request.session().attribute("login"));
            model.put("statuses", Status.getStatusMap());
            return render(model, "redact.ftl");
        });

        post("/issue/redact/:id", (request, response) ->
        {
            if (request.session().attribute("login") == null) {
                response.redirect("/login");
            }

            Map<String, Object> issueMap = new HashMap<>();
            issueService.redact(request);
            response.redirect("/issue/" + request.params(":id"));
            return null;
        });

        post("/comment/:id", (request, response) ->
        {
            if (request.session().attribute("login") == null) {
                response.redirect("/login");
            }

            Map<String, Object> model = new HashMap<>();
            commentService.createComment(request);
            response.redirect("/issue/" + request.params(":id"));
            return null;
        });
    }

    private static String render(Map<String,?> model, String templatePath) {
        return new FreeMarkerEngine().render(new ModelAndView(model, templatePath));
    }
}

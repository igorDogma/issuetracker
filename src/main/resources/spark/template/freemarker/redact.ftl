<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Redact issue</title>
</head>
<body>
<#if user??>
<h4>You login as ${user}. <a href="/logout">Logout</a></h4>
</#if>
<form method='post' action="/issue/redact/${issue.id}">
    Title:    <input type='text' name='title' value=${issue.title}></br>
    Status:
    <select name='status'>
        <option label=${issue.status} value=${issue.status} >${issue.status}</option>
        <#list statuses as status>
            <#if status != issue.status>
                <option value=${status}>${status}</option>
            </#if>
        </#list>
    </select></br>
    Description: </br>   <input type='textarea' name='description' value=${issue.description}></br>
    <button>Redact</button>
</form>
</body>
</html>
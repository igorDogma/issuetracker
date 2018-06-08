<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${issue.title}</title>
</head>
<body>

<#if user??>
<h4>You login as ${user}. <a href="/logout">Logout</a></h4>
</#if>

<h2>Issue #${issue.id}</h2>
<div>
    <h4>Title:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>${issue.title}</b></h4>
    <h4>Author:&nbsp;&nbsp;                 <b>${issue.account.login}</b></h4>
    <h4>Created:&nbsp;                      <b>${issue.created}</b></h4>
    <h4>Status:&nbsp;&nbsp;                 <b>${issue.status}</b></h4>
    <h4>Description:</h4>
    <div>${issue.description}</div>
</div>
</br>
</br>
</br>
<#if comments??>
 <#list comments as comment>
    <div>
        <h4>Status:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>${comment.status}</b></h4>
        <h4>Author:&nbsp;&nbsp;                 <b>${comment.account.login}</b></h4>
        <h4>Created:&nbsp;                      <b>${comment.created}</b></h4>
        <h4>Description:</h4>
        <div>${comment.description}</div>
    </div>
</br>
</br>
 </#list>
</#if>

</br>
</br>
<form method='post' action="/comment/${issue.id}">
    Status:
    <select name='status'>
        <option label=${issue.status} value=${issue.status} >${issue.status}</option>
        <#list statuses as status>
            <#if status != issue.status>
                <option value=${status}>${status}</option>
            </#if>
        </#list>
    </select></br>
    Description: </br>   <input type='textarea' name='description'></br>
    <button>Redact</button>
</form>

</br>
</br>
<div class="footer">
    <a href="/">Home</a>
    <a href="/create">Create issue</a>
    <a href="/issue/redact/${issue.id}">Redact issue</a>
</div>
</body>
</html>
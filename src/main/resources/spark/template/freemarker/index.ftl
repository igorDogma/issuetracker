<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<#if user??>
<h4>You login as ${user}. <a href="/logout">Logout</a></h4>
</#if>

<h2>Issues Tracker</h2>
<div>
    <#list issues as issue>
        <div><a href="/issue/${issue.id}">${issue.title}</a> <div>${issue.status}</div> <div>${issue.created}</div></div></br>
    </#list>
</div>
<div class="footer">
<a href="/create">Create issue</a>
</div>
</body>
</html>
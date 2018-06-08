<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create issue</title>
</head>
<body>
<#if user??>
<h4>You login as ${user}. <a href="/logout">Logout</a></h4>
</#if>
<#if message??>
    <h4>${message.message}</h4>
</#if>
<form method='post' action="/create">
    Title:    <input type='text' name='title'></br>
    Description:    <input type='text' name='description'></br>
    <button>Create</button>
</form>
</body>
</html>
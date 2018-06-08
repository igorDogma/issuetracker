<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<#if message??>
    <h2>${message.message}</h2>
<#else >
    <h4>Welcom to us! Login or <a href="/registration">create account</a></h4>
</#if>

<form method='post' action="/login">
    Login:    <input type='text' name='login'></br>
    Password: <input type='password' name='password'></br>
    <button>Submit</button>
</form>
</body>
</html>
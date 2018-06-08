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
    <h2>Please, fill all fields!</h2>
</#if>
<a href="/login">Login</a>
<form method='post' action="/registration">
    Login:    <input type='text' name='login'></br>
    Email:    <input type='email' name='email'></br>
    Password: <input type='password' name='password'></br>
    <button>Submit</button>
</form>
</body>
</html>
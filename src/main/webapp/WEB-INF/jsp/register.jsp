<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/29
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>register</title>
    <meta charset="UTF-8">

</head>
<body>
<div style="margin:auto">
    <form action="/user/register" method="post">
        <fieldset  style="width:400px;margin:auto;" align="center">
            <label for="username">用&nbsp;&nbsp;户&nbsp;&nbsp;名：</label>
            <input id="username" name="username"  placeholder="请输入用户名" type="text" required="required" pattern="^[a-zA-Z0-9]\w{0,17}$" oninvalid="setCustomValidity('0~18字母数字或下划线')" oninput="setCustomValidity('')"required autofocus>
            <br>
            <br>
            <label for="password">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：
            </label><input id="password" type="password" placeholder="请输入密码" required="required" onchange="checkPasswords()" pattern="^[a-zA-Z0-9]\w{5,17}$" oninvalid="setCustomValidity('6~18字母数字或下划线')" oninput="setCustomValidity('')" name="password">
            <br>
            <br>
            <label for="checkPassword">确认密码：
            </label><input id="checkPassword" type="password" placeholder="请再次输入密码" required="required" onchange="checkPasswords()" name="checkPassword">
            <br>
            <br>
            <label for="email">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</label>
            <input id="email" name="email" type="text" placeholder="请输入邮箱" required="required">
            <br>
            <br>
            <button type="submit" onclick="document.passwordChange.password1.checkValidity()">注册</button>

        </fieldset>
    </form>
</div>
<script type="text/javascript" color="139,131,120" opacity='1' zIndex="-2" count="300" src="https://cdn.bootcss.com/canvas-nest.js/1.0.1/canvas-nest.min.js"></script>
<script>
    function checkPasswords() {
        var password = document.getElementById("password");
        var checkPassword = document.getElementById("checkPassword");

        if (password.value != checkPassword.value)
            checkPassword.setCustomValidity("两次输入的密码不匹配");
        else
            checkPassword.setCustomValidity("");
    }
</script>
</body>
</html>

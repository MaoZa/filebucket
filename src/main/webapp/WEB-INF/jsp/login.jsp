<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/29
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>login</title>
        <meta charset="UTF-8">
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.js"></script>
        <script language="JavaScript" >

            function GetJsonData() {
                var json = {
                    "username": $("#username").val(),
                    "password": $("#password").val()
                };
                return json;
            }

            $(document).ready(function(){
                $("#loginForm").submit(function (envent) {
                    envent.preventDefault();
                    var form = $(this);
                    $.ajax({
                        url: form.attr("action"),
                        headers:{
                            "Content-Type":"application/json"
                        },
                        type: "POST",
                        data: JSON.stringify(GetJsonData()),
                        dataType: "json",
                        success: function (data) {
                            if(data.code == 0){
                                alert("登录成功");
                                window.location.href='/table';
                            }else{
                                alert(data.message);
                            }
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
    <div style="margin:auto">
        <form id="loginForm" action="/user/login" method="post">
            <fieldset  style="width:400px;margin:auto;" align="center">
                <label for="username">用户名：</label>
                <input id="username" name="username" type="text" required="required" required autofocus>
                <br>
                <br>
                <label for="password">密&nbsp;&nbsp;&nbsp;码：
                </label><input id="password" type="password" required="required" name="password">
                <br>
                <br>
                <input type="submit" value="登陆">&nbsp;&nbsp;&nbsp;&nbsp;<a href="/register"><b>注册</b></a>
            </fieldset>
        </form>
    </div>
    <script type="text/javascript" color="139,131,120" opacity='1' zIndex="-2" count="300" src="https://cdn.bootcss.com/canvas-nest.js/1.0.1/canvas-nest.min.js"></script>
    </body>
</html>

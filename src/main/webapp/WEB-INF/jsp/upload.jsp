<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/29
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传文件</title>
</head>
<body>

    <form action="/files/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file" id="file">
        <input type="submit" value="上传">
    </form>

</body>
</html>

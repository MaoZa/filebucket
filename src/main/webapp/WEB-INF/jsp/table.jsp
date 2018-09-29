<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/29
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>文件列表</title>
    <meta charset="UTF-8">
</head>
<body>
<table border="2" style="width:500px;margin:auto;text-align:middle;" >
    <p><a href="/upload">上传文件</a></p>
    <tr>
        <th>文件id</th>
        <th>文件名</th>
        <th>上传时间</th>
        <th>文件大小(字节)</th>
        <th>文件链接</th>
        <th>删除文件</th>
    </tr>
    <c:forEach items="${filesList}" var="file">
        <tr>
            <th>${file.id}</th>
            <th>${file.fileName}</th>
            <th>${file.createTime}</th>
            <th>${file.fileSize}</th>
            <th><a href="${file.fileUrl}">点击下载</a></th>
            <th><a href="/files/delete/${file.id}">删除</a></th>
        </tr>
    </c:forEach>

</table>
</body>
</html>
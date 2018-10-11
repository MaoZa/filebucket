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
    <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.js"></script>
    <script language="JavaScript" >

        $(document).ready(function(){
                var form = $(this);
                $.ajax({
                    url: "/files/findFiles?pageNum=" + 1 + "&pageSize=" + 1,
                    headers:{
                        "Content-Type":"application/x-www-form-urlencoded"
                    },
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        if(data.code == 0){
                            if(data.data.filesList.size == 0){
                                alert("未上传任何文件,请先上传文件");
                                // window.location.href='/upload';
                            }else{
                                var arr = data.data.filesList.list;
                                arr.forEach(function(value,i){
                                    var tempTr = document.createElement("tr")
                                    var tempNode = null;
                                    var tempSub = document.createElement("th")
                                    "<th>" + value.id + "</th>"
                                    tempNode = document.createTextNode(value.id)
                                    var tempSub = document.createElement("th")
                                    tempSub.append(tempNode)
                                    tempTr.append(tempSub);
                                    tempNode = document.createTextNode(value.fileName)
                                    tempSub = document.createElement("th")
                                    tempSub.append(tempNode)
                                    tempTr.append(tempSub);
                                    tempNode = document.createTextNode(value.createTime)
                                    tempSub = document.createElement("th")
                                    tempSub.append(tempNode)
                                    tempTr.append(tempSub);
                                    tempNode = document.createTextNode(value.fileSize)
                                    tempSub = document.createElement("th")
                                    tempSub.append(tempNode)
                                    tempTr.append(tempSub);
                                    tempNode = document.createTextNode("下载文件")
                                    tempA = document.createElement("a")
                                    tempA.href = value.fileUrl
                                    tempSub = document.createElement("th")
                                    tempA.append(tempNode)
                                    tempSub.append(tempA)
                                    tempTr.append(tempSub);
                                    tempNode = document.createTextNode("删除文件")
                                    tempA = document.createElement("a")
                                    tempA.href = "/files/delete/" + value.id
                                    tempSub = document.createElement("th")
                                    tempA.append(tempNode)
                                    tempSub.append(tempA)
                                    tempTr.append(tempSub);
                                    $("#table").append(tempTr);
                                })
                            }
                        }else{
                            alert(data.message);
                        }
                    }
                });

                $.ajax({
                    url: "/files/findSumSize",
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        if(data.code == 0){
                            var text = document.createTextNode("储存空间:" + data.data.sumSize + "/" + data.data.userMaxSize);
                           $("#SumSize").append(text);
                        }
                    }
                });
        });
    </script>
</head>
<body>
<table id="table" border="2" style="width:500px;margin:auto;text-align:middle;" >
    <center>
        <form action="/files/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file" id="file">
        <input type="submit" value="上传">
        </form>
        <p>点击上传后请等待自动刷新</p>
        <p id="SumSize"></p>
    </center>
    <br />
    <tr>
        <th>文件id</th>
        <th>文件名</th>
        <th>上传时间</th>
        <th>文件大小(字节)</th>
        <th>文件链接</th>
        <th>删除文件</th>
    </tr>

</table>
</body>
</html>
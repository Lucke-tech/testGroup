<%--
  Created by IntelliJ IDEA.
  User: msi-pc
  Date: 2017/11/15
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/homePage.css">
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.cookie.js" type="text/javascript"></script>
    <script>
        $(function () {
            $("#loginOut").click(function () {
                $.cookie('UserName',null,{expires: -1,path: '/'});
                $.cookie('UserInfo',null,{expires: -1,path: '/'});
                window.location.reload();
            })
        })
    </script>
</head>
<body>
<div style="position: fixed;  left:2%; top:2%; width: 10vw; height: 10vh; ;overflow: hidden">
    <a href="${pageContext.request.contextPath}/index/user/userCenter.jsp"><div style="position:absolute;border-radius: 50%;  background:url(${pageContext.request.contextPath}/${sessionScope.userHeadPic[0].userHeadPic});height: 10vh;width:10vh;background-size: cover;display: inline-block;z-index: 1"></div></a>
    <div style="position: relative;height: 10vh;width: 100%;display: inline-block; z-index: -2;left: 25%">
        <a href="t1"><button id="userCenter" style="position:relative; float: top; width: 100%;height: 50%;display: inline-block;z-index: 0;border: none;cursor: pointer;outline:none;"></button></a>
        <div><button id="loginOut" style="position:relative; float: top; width: 100%;height: 50%;display: inline-block;z-index: 0;border: none;cursor: pointer;outline:none;"></button></div>
    </div>
</div>

<div id="list">
    <a href="${pageContext.request.contextPath}/index/user/userCenter.jsp">
        <div id="character" class="choose">
            <img src="/img/character/2b.png">
            <div class="chooseText">Character</div>
        </div>
    </a>

    <a href="#">
        <div id="world" class="choose">
            <img src="${pageContext.request.contextPath}/img/character/9s.png">
            <div class="chooseText">World</div>
        </div>
    </a>
    <a href="#">
        <div id="stroy" class="choose">
            <img src="${pageContext.request.contextPath}/img/character/a2.png">
            <div class="chooseText">Story</div>
        </div>
    </a>
    </a>
    <a href="#">
        <div id="staff" class="choose">
            <img src="${pageContext.request.contextPath}/img/character/pascal.png">
            <div class="chooseText">Music</div>
        </div>
    </a>
</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: msi-pc
  Date: 2017/11/14
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/indexPageStyles.css">
    <title>LuckE</title>
      <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js" type="text/javascript"></script>
      <script>
          function validatorLoginName(){
              var userNameInputer = document.getElementById("inputName");
              var hiddenspan = document.getElementById("loginAlert");
              var loginName = userNameInputer.value;

              if(loginName.length < 5) {
                  showHiddenSpan(hiddenspan,"用户名应大于5字符");
                  return;
              }

              if(!loginName.match("^[a-zA-Z0-9_]{5,15}$")) {
                  showHiddenSpan(hiddenspan,"用戶名只能包含数字、字母、下划线");
                  return;
              }
              hiddenspan.style.width = "0px";
              hiddenspan.style.height = "0px";
              hiddenspan.style.fontSize = "0px";

          }

          $(document).ready(function(){
              $("#submit").click(function(){
                  var hiddenspan = document.getElementById("loginAlert");
                  $.ajax({
                      type: "POST",
                      url: "${pageContext.request.contextPath}/DoLogin" + '?userName' + 'password',
                      data: $('#login').serialize(),
                      success: function(data){
                          if(data=="true"){
                              window.location.href = "${pageContext.request.contextPath}/index/web/homePage.jsp";
                              return;
                          } else {
                              showHiddenSpan(hiddenspan, "用户名或密码错误");
                              return;
                          }
                      }
                  });
              });
          });

          $(document).keyup(function(event){
              if(event.keyCode ==13){
                  $("#submit").trigger("click");
              }
          });

          function showHiddenSpan(span ,str) {
              span.style.fontSize = "15px";
              span.style.outline = "none";
              span.style.width = "auto";
              span.style.height = 'auto';
              span.style.color = "red";
              span.innerHTML = str;
          }
      </script>
  </head>
  <body>
  <div id="loginform">
  <form id ="login">
      <div class="content1">
          <input type="text" id="inputName" name="userName" placeholder="用户名" class="inputText" onblur="validatorLoginName()">
          <br>
          <span id="loginAlert"></span>
      </div>
      <div class="content1">
          <input id="password" type="password" name="password" placeholder="密码" class="inputText">
      </div>
      <div class="content2">
          <span>保存登录信息<input type="checkbox" name="saveCookie" value="Y"></span>
          <button type="button" id="submit" value="登录">登录</button>
          <a href="${pageContext.request.contextPath}/index/user/signUp.jsp"><button type="button" value="注册" id="signup">注册</button></a>
      </div>
  </form>
  </div>
  </body>
</html>

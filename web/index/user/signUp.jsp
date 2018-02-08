<%--
  Created by IntelliJ IDEA.
  User: msi-pc
  Date: 2017/11/15
  Time: 20:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/signUpPageStyles.css">
        <title>注册</title>
        <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js" type="text/javascript"></script>
        <script>
            function empty() {

            }

            window.onload=function(){
                inputPass.oninvalid=function() {
                    inputPass.setCustomValidity("密码只能包括a-z,A-Z,0-9以及'_'");
                };
            };

            function validatorSignUpName(){
                var userNameInputer = document.getElementById("inputName");
                var hiddenspan = document.getElementById("inputNameAlert");
                var signUpName = userNameInputer.value;
                var hiddenvalue = document.getElementById("hiddenDate");

                if(signUpName.length < 5) {
                    showHiddenPic(0);
                    showHiddenSpan(hiddenspan,"用户名要大于5个字符");
                    hiddenvalue.value = 1;
                    return;
                }

                if(!signUpName.match("^[a-zA-Z0-9_]{5,15}$")) {
                    showHiddenPic(0);
                    showHiddenSpan(hiddenspan,"用戶名只能包含数字、字母、下划线");
                    hiddenvalue.value = 2;
                    return;
                }

                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/ValidateName",
                    data: "signUpName="+signUpName,
                    success: function(data){
                        if(data=="true") {
                            showHiddenPic(0);
                            showHiddenSpan(hiddenspan, "用户名已存在");
                            document.getElementById('inputName').focus();
                            return;
                        }
                    }
                });

                showHiddenPic(1);

                hiddenvalue.value = 0;
                hiddenspan.style.width = "0px";
                hiddenspan.style.height = "0px";
                hiddenspan.style.fontSize = "0px";
            }

            function validatorPassword() {
                var passwordF = document.getElementById("inputPass");
                var passwordHP = document.getElementById("hiddenPassDate");
                var hiddenspan = document.getElementById("inputPassAlert");

                if (passwordF.value.length < 6) {
                    showHiddenSpan(hiddenspan, "密码至少有6个字符");
                    passwordHP.value = 1;
                    return;
                }

                if (!passwordF.value.match("^[a-zA-Z0-9_]{6,15}$")) {
                    showHiddenSpan(hiddenspan, "只能包含数字、字母、下划线");
                    passwordHP.value = 2;
                    return;
                }

                passwordHP.value = 0;
                hiddenspan.style.width = "0px";
                hiddenspan.style.height = "0px";
                hiddenspan.style.fontSize = "0px";
            }

            function validatorConfirmPass() {
                var hiddenspan = document.getElementById("inputPassConfirmAlert");
                var passwordC = document.getElementById("confirmPassword");
                var passwordF = document.getElementById("inputPass");
                var passwordH = document.getElementById("hiddenPassConfirmDate");

                if (!(passwordF.value==(passwordC.value))) {
                    passwordH.value = 1;
                    showHiddenSpan(hiddenspan, "两次输入的密码不同");
                    return;
                } else {
                    passwordH.value = 0;
                    hiddenspan.style.width = "0px";
                    hiddenspan.style.height = "0px";
                    hiddenspan.style.fontSize = "0px";
                }
            }

            $(document).ready(function(){
                $("#submit").click(function(){
                    var passwordF = document.getElementById("inputPass").value;
                    var userName = document.getElementById("inputName").value;
                    var passwordC = document.getElementById("confirmPassword").value;
                    var hiddenspanSignUp = document.getElementById("inputNameAlert");
                    var hiddenspanPassword = document.getElementById("inputPassAlert");
                    var hiddenspanConfirm = document.getElementById("inputPassConfirmAlert");
                    var i = 0;
                    if(userName.length < 5) {
                        showHiddenSpan(hiddenspanSignUp,"用户名要大于5个字符");
                        document.getElementById('inputName').focus();
                        showHiddenPic(0);
                        return;
                    }

                    if(!userName.match("^[a-zA-Z0-9_]{5,15}$")) {
                        showHiddenSpan(hiddenspanSignUp,"用戶名只能包含数字、字母、下划线");
                        document.getElementById('inputName').focus();
                        showHiddenPic(0);
                        return;
                    }

                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/ValidateName",
                        data: "signUpName="+userName,
                        success: function(data){
                            if(data=="true") {
                                i = 1;
                                showHiddenPic(0);
                                showHiddenSpan(hiddenspanSignUp, "用户名已存在");
                                document.getElementById('inputName').focus();
                                return;
                            }
                        }
                    });

                    showHiddenPic(1);
                    if (i==1) {
                        return;
                    }

                    hiddenspanSignUp.style.width = "0px";
                    hiddenspanSignUp.style.height = "0px";
                    hiddenspanSignUp.style.fontSize = "0px";

                    if (passwordF.length < 6) {
                        showHiddenSpan(hiddenspanPassword, "密码至少有6个字符");
                        document.getElementById('inputPass').focus();
                        return;
                    }

                    if (!passwordF.match("^[a-zA-Z0-9_]{6,15}$")) {
                        showHiddenSpan(hiddenspanPassword, "只能包含数字、字母、下划线");
                        document.getElementById('inputPass').focus();
                        return;
                    }

                    hiddenspanPassword.style.width = "0px";
                    hiddenspanPassword.style.height = "0px";
                    hiddenspanPassword.style.fontSize = "0px";

                    if (!(passwordF==(passwordC))) {
                        showHiddenSpan(hiddenspanConfirm, "两次输入的密码不同");
                        document.getElementById('confirmPassword').focus();
                        return;
                    } else {
                        hiddenspanConfirm.style.width = "0px";
                        hiddenspanConfirm.style.height = "0px";
                        hiddenspanConfirm.style.fontSize = "0px";
                    }

                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/DoRegister" + '?hiddendate' + 'hiddenPassDate' + 'hiddenPassConfirmDate' + 'userName' + 'password' + 'confirmPassword',
                        data: $('#signUpForm').serialize(),
                        success: function(data){
                            if(data=="true"){
                                window.location.href = "/index.jsp";
                                return;
                            }else{
                                showHiddenSpan(hiddenspanSignUp, "用户名已存在");
                                showHiddenPic(0);
                                document.getElementById('inputName').focus();
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
                span.style.width = "300px";
                span.style.height = "40px";
                span.style.color = "red";
                span.innerHTML = str;
            }

            function showHiddenPic(flag) {
                var judgePic = document.getElementById("judgePic");
                if (flag==1) {
                    judgePic.src = "${pageContext.request.contextPath}/img/true.png";
                    judgePic.style.width = "20px";
                    judgePic.style.height = "20px";
                    judgePic.style.top = "3px";
                } else {
                    judgePic.src = "${pageContext.request.contextPath}/img/false.png";
                    judgePic.style.width = "20px";
                    judgePic.style.height = "20px";

                }
            }
        </script>
    </head>
    <body>
    <div id="signUpDiv">
        <form id="signUpForm">
            <input type="text" id="hiddenDate" name="hiddenDate" value="0" style="height:0px;width:0px;padding:0px;border:0px;display:none">
            <input type="text" id="hiddenPassDate" name="hiddenPassDate" value="0" style="height:0px; width:0px;padding:0px;border:0px;display:none" >
            <input type="text" id="hiddenPassConfirmDate" name="hiddenPassConfirmDate" value="0" style="height:0px; width:0px;padding:0px;border:0px;display:none" >
            <span id="WelcomeTitle">WELCOME!</span>
            <div class="content1">
                <input type="text" id="inputName" name="userName" placeholder="请输入用户名，可以包括数字,字母与下划线" class="inputText" onblur="validatorSignUpName()">
                <img src="" id="judgePic">
                <br>
                <span id="inputNameAlert"></span>
            </div>
            <div class="content1">
                <input type="password" id="inputPass" name="password" placeholder="请输入密码(6-15位)" class="inputText" onblur="validatorPassword()">
                <br>
                <span id="inputPassAlert"></span>
            </div>
            <div class="content1">
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="请确认输入的密码" class="inputText"} onblur="validatorConfirmPass()">
                <br>
                <span id="inputPassConfirmAlert"></span>
            </div>
            <div class="content2">
                <button type="button" id="submit" >注册</button>
            </div>
        </form>
    </div>
    </body>
</html>

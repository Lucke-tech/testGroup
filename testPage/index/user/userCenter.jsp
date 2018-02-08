<%--
  Created by IntelliJ IDEA.
  User: msi-pc
  Date: 2017/11/22
  Time: 21:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>个人中心</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/userCenterStyles.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.js" type="text/javascript"></script>
    <script>

        $(function () {
            $("#submit").click(function () {
                var formData = new FormData($("#userInfoForm")[0]);
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/UploadServlet",
                    data: formData,
                    async:false,
                    cache:false,
                    contentType: false,
                    processData: false,
                    success: function(data){
                        if(data=="true"){
                            alert("a")
                            return;
                        }else{
                            alert("b")
                            return;
                        }
                    }
                });
            })

            $("#uploadBtn").click(function () {
                $("#ChooseHeadPic").click();
            })

            $('#ChooseHeadPic').change(function(event) {
                var files = event.target.files, file;
                if (files && files.length > 0) {
                    file = files[0];
                    // if(file.size > 1024 * 1024 * 2) {
                    //     alert('图片大小不能超过 2MB!');
                    //     return false;
                    // }
                    var URL = window.URL;
                    var imgURL = URL.createObjectURL(file);
                    console.log(imgURL);
                    $(".HeadPic").attr("src", imgURL);
                }
            });
        });
    </script>
</head>
<body>
    <div id="container">
        <div class="bg bg-blur"></div>
        <div id="contains">
            <form id="userInfoForm">
                <div class="userHead">
                    <img src="${pageContext.request.contextPath}/${sessionScope.userHeadPic[0].userHeadPic}" class="HeadPic">
                    <span class="space">          </span>
                    <a href="#" id="fileUploader"><div id="uploadBtn"><img src="${pageContext.request.contextPath}/img/upLoad.png" style="width: 25px; height: 25px;">上传头像</div></a>
                    <input type="file" id="ChooseHeadPic" class="containsEle" multiple="multiple" name="upload1">
                </div>
                <div class="userHead">
                    <img src='${pageContext.request.contextPath}/${sessionScope.userHeadPic[0].userHeadPic}' class="HeadPic">
                    <span class="space">          </span>
                    <a id="fileDownload"><div id="downloadBtn"><img src="${pageContext.request.contextPath}/img/upLoad.png" style="width: 25px; height: 25px;">下载头像</div></a>
                </div>
                <input type="submit" value="提交" id="submit" >

            </form>
        </div>
    </div>
</body>
</html>

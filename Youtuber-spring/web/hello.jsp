<%--
  Created by IntelliJ IDEA.
  User: a8001
  Date: 2020/8/24
  Time: 上午 09:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>jQCloud Example</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/jQCloud-master/jqcloud/jqcloud.css"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/jQCloud-master/jqcloud/jqcloud-1.0.4.js"></script>
    <script type="text/javascript">

    </script>
</head>
<body>
<div class="slider-container">
    <div class="slider-control left inactive"></div>
    <div class="slider-control right"></div>
    <ul class="slider-pagi"></ul>
    <div class="slider">
        <div class="slide slide-0 active">
            <div class="slide__bg"></div>
            <div class="slide__content">
                <svg class="slide__overlay" viewBox="0 0 720 405" preserveAspectRatio="xMaxYMax slice">
                    <path class="slide__overlay-path" d="M0,0 150,0 500,405 0,405" />
                </svg>
                <div class="slide__text">
                    <h2 class="slide__text-heading" style="font-size: 69px">Youtuber</br>留言區</br>關鍵字分析</h2>
                </div>
            </div>
            <form class="select" action="webController" method="get" style="height: auto;width: 120px">

                <select id="slct" name="typeId" style="font-size:20px;text-align:center;">

                    <option value="jaga" >蔡阿嘎</option>
                    　
                    <option value="jpeo">這群人</option>
                    　
                    <option value="jadi">阿滴英文</option>
                    　
                    <option value="jgod">阿神</option>
                    　
                    <option value="jrock">滾石唱片</option>
                    <br/><input type="submit" value="Submit">
                </select>
            </form>
        </div>

    </div>

</div>






</body>
</html>

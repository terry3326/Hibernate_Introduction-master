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
        <%-- 文字雲內容參數 --%>
        var word_list = [
            <c:forEach items="${requestScope.list}" var="pojo">
            {
                text: "${pojo.item}",
                weight: "<fmt:formatNumber type="number" value="${pojo.sum/100+1}" maxFractionDigits="0"/>"
            },
            </c:forEach>
        ];
        $(function () {
            // When DOM is ready, select the container element and call the jQCloud method, passing the array of words as the first argument.
            $("#example").jQCloud(word_list);
        });
    </script>
</head>
<body>
<%--標題--%>
<h1 style="font-size: 52px;color: blue">${requestScope.youtubername}留言區關鍵字分析</h1>
<div id="example" style="width: 650px; height: 600px;"></div>

<%--排行榜內容--%>
<div class="leaderboard">
    <h2>Leaderboard </h2>
    <ol>

        <c:forEach items="${requestScope.leaderboard}" var="leader">
            <li>
                <span class="name">"${leader.item}"</span>
                <span class="percent">"${leader.sum}"</span>
            </li>
        </c:forEach>

    </ol>
</div>

<%--內容下拉選單--%>
<form class="select" action="webController" method="get" style="height: auto;width: 120px">

    <select id="slct" name="typeId" style="font-size:20px;text-align:center;">

        <option value="jaga">蔡阿嘎</option>
        　
        <option value="jpeo">這群人</option>
        　
        <option value="jadi">阿滴英文</option>
        　
        <option value="jgod">阿神</option>
        　
        <option value="jrock">滾石唱片</option>
        <br/><input type="submit" value="Submit">
    </select>
</form>

</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Board Search Results</title>
</head>
<body>
<h2>Board Search</h2>
<form action="http://localhost:8080/boards/free/list" method="get">
    <label for="regDateStart">Registration Date Start:</label>
    <input type="date" id="regDateStart" name="regDateStart"><br><br>

    <label for="regDateEnd">Registration Date End:</label>
    <input type="date" id="regDateEnd" name="regDateEnd"><br><br>

    <label for="categoryName">Category Name:</label>
    <input type="text" id="categoryName" name="categoryName"><br><br>

    <label for="keyword">Keyword:</label>
    <input type="text" id="keyword" name="keyword"><br><br>

    <input type="submit" value="Search">
</form>

<h2>게시글 검색</h2>
<p>Total Count: ${boardListDto.totalCount}</p>
<table border="1">
    <tr>
        <th>Category</th>
        <th>Title</th>
        <th>File Count</th>
        <th>Writer</th>
        <th>Views</th>
        <th>Reg Date</th>
        <th>Update Date</th>
    </tr>
    <c:forEach var="board" items="${boardListDto.boardList}">
        <tr>
            <td>${board.category}</td>
            <td>${board.title}</td>
            <td>${board.fileCount}</td>
            <td>${board.writer}</td>
            <td>${board.views}</td>
            <td>${board.regDateAsString}</td>
            <td>${board.updateDateAsString}</td>
        </tr>
    </c:forEach>
</table>

<div class="pagination">
    <c:if test="${boardListDto.currentPage > 1}">
        <a href="?page=${boardListDto.currentPage - 1}">Previous</a>
    </c:if>

    <c:forEach var="i" begin="${boardListDto.sectionPageBegin}" end="${boardListDto.sectionPageEnd}">
        <c:choose>
            <c:when test="${i == boardListDto.currentPage}">
                <span>${i}</span>
            </c:when>
            <c:otherwise>
                <a href="?page=${i}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:if test="${boardListDto.currentPage < boardListDto.totalPage}">
        <a href="?page=${boardListDto.currentPage + 1}">Next</a>
    </c:if>
</div>
</body>
</html>

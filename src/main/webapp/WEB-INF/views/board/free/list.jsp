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
    <label for="regDateStart">등록일:</label>
    <input type="date" id="regDateStart" name="regDateStart"><br><br>

    <label for="regDateEnd">최근 수정일:</label>
    <input type="date" id="regDateEnd" name="regDateEnd"><br><br>

    <label for="categoryName">카테고리:</label>
    <select id="categoryName" name="categoryName">
        <option value="">--카테고리를 선택해 주세요--</option>
        <c:forEach var="category" items="${categories}">
            <option value="${category}">${category}</option>
        </c:forEach>
    </select>
    <br><br>

    <label for="keyword">검색어:</label>
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
            <td>
                <a href="http://localhost:8080/boards/free/views/${board.boardId}">${board.title}</a>
            </td>
            <td>${board.fileCount}</td>
            <td>${board.writer}</td>
            <td>${board.views}</td>
            <td>${board.regDateAsString}</td>
            <td>${board.updateDateAsString}</td>
        </tr>
    </c:forEach>
</table>

<div class="pagination">
    <c:if test="${boardListDto.paginationDto.currentSection != 1}">
        <a href="?page=${boardListDto.paginationDto.currentSectionPageBegin - 1}">&lt</a>
    </c:if>

    <c:forEach var="i" begin="${boardListDto.paginationDto.currentSectionPageBegin}" end="${boardListDto.paginationDto.currentSectionPageEnd}">
        <c:choose>
            <c:when test="${i == boardListDto.paginationDto.currentPage}">
                <span>${i}</span>
            </c:when>
            <c:otherwise>
                <a href="?page=${i}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:if test="${boardListDto.paginationDto.currentSection != boardListDto.paginationDto.totalSection}">
        <a href="?page=${boardListDto.paginationDto.currentSectionPageEnd + 1}">&gt</a>
    </c:if>
    
    ...
    <a href="?page=${boardListDto.paginationDto.totalPage}">${boardListDto.paginationDto.totalPage}</a>
</div>
</body>
</html>

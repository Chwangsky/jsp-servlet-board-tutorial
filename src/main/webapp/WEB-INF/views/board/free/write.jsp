<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>게시판 - 등록</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">게시판 - 등록</h2>

        <!-- 에러 메시지가 있을 경우 표시 -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${errorMessage}" escapeXml="false" />
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/boards/free/write" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
            <div class="form-group">
                <label for="categoryName">카테고리:</label>
                <select id="categoryName" name="categoryName" class="form-control" required>
                    <option value="">--카테고리를 선택해 주세요--</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category}">${category}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="author">작성자</label>
                <input type="text" id="author" name="author" class="form-control" value="${author}">
            </div>

            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" class="form-control" value="${password}">
            </div>

            <div class="form-group">
                <label for="password-repeat">비밀번호 확인</label>
                <input type="password" id="password-repeat" name="password" class="form-control">
            </div>

            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" id="title" name="title" class="form-control" value="${title}">
            </div>

            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" name="content" class="form-control" rows="10">${content}</textarea>
            </div>

            <div class="form-group">
                <label for="files">파일 첨부</label>
                <div id="fileContainer">
                    <input type="file" name="files" class="file-input form-control mb-2">
                    <input type="file" name="files" class="file-input form-control mb-2">
                    <input type="file" name="files" class="file-input form-control mb-2">
                </div>
                <button type="button" class="btn btn-secondary mt-2" onclick="addFileInput()">파일 추가하기</button>
            </div>

            <button type="submit" class="btn btn-primary">저장</button>
        </form>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

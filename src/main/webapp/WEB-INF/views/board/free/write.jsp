<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>게시판 - 등록</title>

    <script>
        function addFileInput() {
            let fileInputs = document.getElementsByClassName('file-input');
            if (fileInputs.length < 10) {
                let newInput = document.createElement('input');
                newInput.setAttribute('type', 'file');
                newInput.setAttribute('name', 'files');
                newInput.className = 'file-input';
                document.getElementById('fileContainer').appendChild(newInput);
                document.getElementById('fileContainer').appendChild(document.createElement('br'));
            } else {
                alert("최대 10개의 파일까지만 등록할 수 있습니다.");
            }
        }

        function validateForm() {
            let fileInputs = document.getElementsByClassName('file-input') 

        }
    </script>
    
</head>
<body>
    <h2>게시판 - 등록</h2>
    <form action="${pageContext.request.contextPath}/boards/free/write" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
        <label for="categoryName">카테고리:</label>
        <select id="categoryName" name="categoryName">
            <option value="">--카테고리를 선택해 주세요--</option>
            <c:forEach var="category" items="${categories}">
                <option value="${category}">${category}</option>
            </c:forEach>
        </select>

        <label for="author">작성자</label><br>
        <input type="text" id="author" name="author" required><br><br>

        <label for="password">비밀번호</label><br>

        <input type="password" id="password" name="password" required><br><br>

        <input type="password" id="password-repeat" name="password" required><br><br>

        <label for="title">제목</label><br>
        <input type="text" id="title" name="title" required><br><br>

        <label for="content">내용</label><br>
        <textarea id="content" name="content" rows="10" cols="50" required></textarea><br><br>

        <label for="files">파일 첨부</label><br>
        <div id="fileContainer">
            <input type="file" name="files" class="file-input"><br>
            <input type="file" name="files" class="file-input"><br>
            <input type="file" name="files" class="file-input"><br>
        </div>
        <button type="button" onclick="addFileInput()">추가하기</button><br><br>

        <input type="submit" value="Save">
    </form>
</body>
</html>
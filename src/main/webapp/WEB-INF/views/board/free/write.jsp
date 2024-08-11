<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시판 - 등록v2</title>
    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function validateForm() {
            // 카테고리 선택 여부 확인
            const categoryDropdown = document.getElementById('categoryId');
            if (categoryDropdown.value === '') {
                alert('카테고리를 선택해 주세요');
                return false;
            }

            // 작성자 작성 여부 확인
            const authorInput = document.getElementById('writer');
            if (authorInput.value.trim() === '') {
                alert('작성자 이름을 작성해 주세요');
                return false;
            }

            // 작성자 이름 길이 확인
            if (authorInput.value.length < 3 || authorInput.value.length > 5) {
                alert('작성자 이름은 3글자 이상, 5글자 이하이어야 합니다.');
                return false;
            }
            
            // 비밀번호 확인
            const passwordInput = document.getElementById('password');
            const passwordRepeatInput = document.getElementById('password-repeat');
            if (passwordInput.value.trim() === '') {
                alert('비밀번호를 입력해 주세요');
                return false;
            }

            // 비밀번호와 비밀번호 확인 동일 여부 확인
            if (passwordInput.value !== passwordRepeatInput.value) {
                alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
                return false;
            }

            return true;
        }

        function cancelForm() {
            window.location.href = '${pageContext.request.contextPath}/boards/free/list';
        }
    </script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">게시판 - 등록</h2>

        <form action="${pageContext.request.contextPath}/boards/free/write" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
            <div class="form-group">
                <label for="categoryId">카테고리:</label>
                <select id="categoryId" name="categoryId" class="form-control">
                    <option value="">--카테고리를 선택해 주세요--</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.categoryId}">${category.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="writer">작성자</label>
                <input type="text" id="writer" name="writer" class="form-control">
            </div>
            
            <div class="form-group row">
                <div class="col-md-6">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" class="form-control">
                </div>

                <div class="col-md-6">
                    <label for="password-repeat">비밀번호 확인</label>
                    <input type="password" id="password-repeat" name="password-repeat" class="form-control">
                </div>
            </div>

            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" id="title" name="title" class="form-control">
            </div>

            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" name="content" class="form-control" rows="10"></textarea>
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

            <div class="form-group">
                <button type="submit" class="btn btn-primary">저장</button>
                <button type="button" class="btn btn-danger" onclick="cancelForm()">취소</button>
            </div>
        </form>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

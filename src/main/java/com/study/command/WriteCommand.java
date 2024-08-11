package com.study.command;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.study.entity.BoardCreateEntity;
import com.study.entity.CategoryIdNameEntity;
import com.study.entity.FileEntity;
import com.study.mapper.BoardWriteMapper;
import com.study.util.MyBatisUtil;

public class WriteCommand implements HttpCommand {

    private final static String FILE_UPLOAD_PATH = "C:/fileUpload";

    private SqlSessionFactory sqlSessionFactory;


    public WriteCommand() {
        this.sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    @Override
    public String doService(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String method = request.getMethod();
        if ("GET".equalsIgnoreCase(method)) {
            return doGet(request, response);
        } else if ("POST".equalsIgnoreCase(method)) {
            return doPost(request, response); // 35번째 줄
        } else {
            throw new UnsupportedOperationException("Unsupported method: " + method);
        }
    }

    // 게시글 등록 폼 읽기
    public String doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try (SqlSession session = sqlSessionFactory.openSession()) {
            BoardWriteMapper mapper = session.getMapper(BoardWriteMapper.class);

            List<CategoryIdNameEntity> categories = mapper.getAllCategories();
            request.setAttribute("categories", categories);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ReadCommand 처리 도중 예외가 발생했습니다.", e);
        }

        // 게시글 작성 로직
        return "dispatch:write.jsp";
    }

    // 댓글 등록하기
    private String doPost(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 파라미터 받아오기
        String categoryId = request.getParameter("categoryId");
        String writer = request.getParameter("writer");
        String password = request.getParameter("password");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        // 첨부파일 처리 (파일이 있을 경우)
        List<FileEntity> fileList = new ArrayList<>();
        Collection<Part> parts = request.getParts();

        String uploadPath = FILE_UPLOAD_PATH;
        Files.createDirectories(Paths.get(uploadPath)); // 업로드 디렉토리가 없는 경우 생성

        for (Part part : parts) {
            if (part.getName().equals("files") && part.getSize() > 0) {


                String originalFileName = part.getSubmittedFileName();
                String uuid = UUID.randomUUID().toString();
                String fileName = uuid + "_" + originalFileName;
                String filePath = uploadPath + "/" + fileName;

                // 파일 저장
                part.write(filePath);

                // File 엔티티 객체 생성 및 파일 정보 설정
                FileEntity file = new FileEntity();
                file.setAttachType(Files.probeContentType(Paths.get(filePath))); // 파일 타입
                file.setByteSize((int) part.getSize());
                file.setUuidName(fileName);
                file.setOrgName(originalFileName);
                file.setFileDir(filePath);

                fileList.add(file);
            }
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            BoardWriteMapper mapper = session.getMapper(BoardWriteMapper.class);

            // 게시물 저장
            BoardCreateEntity board = new BoardCreateEntity();
            board.setWriter(writer);
            board.setViews(0);
            board.setPassword(password);
            board.setTitle(title);
            board.setContent(content);
            board.setCategoryId(Integer.parseInt(categoryId));
            mapper.insertBoard(board);

            // 첨부파일 정보 저장
            if (!fileList.isEmpty()) {
                int boardId = board.getBoardId(); // 방금 저장한 게시물의 ID 가져오기
                for (FileEntity file : fileList) {
                    file.setBoardId(boardId);
                    mapper.insertFile(file);
                }
            }

            // 커밋
            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("게시물 작성 중 오류가 발생했습니다.", e);
        }

        // 게시물 목록 페이지로 리다이렉트
        return "redirect:/boards/free/list";
    }
}

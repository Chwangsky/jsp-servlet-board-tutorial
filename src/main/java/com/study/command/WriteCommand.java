package com.study.command;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.study.dto.BoardDetailWrapperDto;
import com.study.entity.BoardDetailEntity;
import com.study.entity.CommentEntity;
import com.study.entity.FileEntity;
import com.study.mapper.BoardReadMapper;
import com.study.util.MyBatisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WriteCommand implements HttpCommand {
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
            return doPost(request, response);
        } else {
            throw new UnsupportedOperationException("Unsupported method: " + method);
        }
    }

    // 댓글 읽기
    public String doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {


        try (SqlSession session = sqlSessionFactory.openSession()) {
            BoardReadMapper mapper = session.getMapper(BoardReadMapper.class);

            int boardId = 0;
            try {
                boardId = Integer.valueOf(request.getPathInfo().split("/")[2]);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("request 형식이 잘못되었습니다. getPathInfo(): " + request.getPathInfo());
            }

            BoardDetailEntity boardDetailEntity = mapper.selectBoardDetailById(boardId);
            List<CommentEntity> commentEntities = mapper.selectCommentsByBoardId(boardId);
            List<FileEntity> fileEntities = mapper.selectFilesByBoardId(boardId);

            BoardDetailWrapperDto boardDetailWrapperDTO = BoardDetailWrapperDto
                    .fromEntities(boardDetailEntity, commentEntities, fileEntities);


            request.setAttribute("boardDetailWrapperDTO", boardDetailWrapperDTO);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ReadCommand 처리 도중 예외가 발생했습니다.", e);
        }

        // 게시글 작성 로직
        return "dispatch:read.jsp";
    }

    // 댓글 등록하기
    private String doPost(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        System.out.println("POST!"); // FIXME
        String boardIdString = request.getParameter("boardId");
        String content = request.getParameter("content");

        try (SqlSession session = sqlSessionFactory.openSession()) {

            int boardId = Integer.parseInt(boardIdString);

            BoardReadMapper mapper = session.getMapper(BoardReadMapper.class);
            mapper.insertComment(boardId, content);

            // 커밋
            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("댓글 작성 중 오류가 발생했습니다.", e);
        }

        // 다시 게시글 페이지로 리다이렉트
        return "redirect:/boards/free/views/" + boardIdString;
    }
}


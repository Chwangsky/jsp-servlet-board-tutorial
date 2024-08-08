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

public class ReadCommand implements HttpCommand {

    private SqlSessionFactory sqlSessionFactory;

    public ReadCommand() {
        this.sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    @Override
    public String doService(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

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
}

package com.study.command;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
            System.out.println(mapper.selectBoardDetailById(1).getTitle()); // thiscode finally
                                                                            // works

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ReadCommand 처리 도중 예외가 발생했습니다.", e);
        }


        // 게시글 작성 로직
        return "dispatch:read.jsp";
    }
}

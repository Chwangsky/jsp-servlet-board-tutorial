package com.study.command;

import org.apache.ibatis.session.SqlSessionFactory;
import com.study.util.MyBatisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListCommand implements HttpCommand {

    private SqlSessionFactory sqlSessionFactory;

    public ListCommand() {
        this.sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    @Override
    public String doService(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        if (request.getQueryString() != null || !request.getQueryString().equals("")) {

            // request에다가 여러 모델들 추가

        }
        // 목록 조회 로직
        return "dispatch:list.jsp";
    }
}


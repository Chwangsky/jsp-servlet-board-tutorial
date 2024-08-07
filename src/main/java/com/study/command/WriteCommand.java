package com.study.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WriteCommand implements HttpCommand {
    @Override
    public String doService(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 게시글 작성 로직
        return "dispatch:write.jsp";
    }
}


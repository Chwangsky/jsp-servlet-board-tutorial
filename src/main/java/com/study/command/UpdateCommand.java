package com.study.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateCommand implements HttpCommand {
    @Override
    public String doService(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 게시글 수정 로직
        String seq = request.getParameter("seq");
        request.setAttribute("seq", seq);
        return "dispatch:update.jsp";
    }
}

package com.study.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteCommand implements HttpCommand {
    @Override
    public String doService(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 게시글 삭제 로직
        String seq = request.getParameter("seq");
        request.setAttribute("seq", seq);
        return "redirect:list"; // 삭제 후 목록으로 리다이렉트
    }
}

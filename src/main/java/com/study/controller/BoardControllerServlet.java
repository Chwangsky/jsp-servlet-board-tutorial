package com.study.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.study.command.DeleteCommand;
import com.study.command.HttpCommand;
import com.study.command.ListCommand;
import com.study.command.ReadCommand;
import com.study.command.UpdateCommand;
import com.study.command.WriteCommand;

@WebServlet("/boards/free/*")
public class BoardControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Map<String, HttpCommand> commandMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        commandMap.put("GET:list", new ListCommand());
        commandMap.put("GET:read", new ReadCommand());
        commandMap.put("GET:insert", new WriteCommand());
        commandMap.put("POST:insert", new WriteCommand());
        commandMap.put("GET:update", new UpdateCommand());
        commandMap.put("PUT:update", new UpdateCommand());
        commandMap.put("DELETE:delete", new DeleteCommand());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpCommand targetService = findTargetService(request);
            if (targetService == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            String view = targetService.doService(request, response);
            handleView(request, response, view);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private HttpCommand findTargetService(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getPathInfo();
        System.out.println(method); // FIXME
        System.out.println(path); // FIXME
        String key = method + ":" + path.substring(1);
        return commandMap.get(key);
    }

    private void handleView(HttpServletRequest request, HttpServletResponse response, String view)
            throws ServletException, IOException {
        if (view.startsWith("redirect:")) {
            response.sendRedirect(view.substring(9));
        } else if (view.startsWith("dispatch:")) {
            request.getRequestDispatcher("/WEB-INF/views/board/free/" + view.substring(9))
                    .forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid view type");
        }
    }
}

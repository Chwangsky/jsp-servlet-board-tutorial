package com.study.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.study.command.HttpCommand;
import com.study.command.ListCommand;
import com.study.command.ReadCommand;
import com.study.command.WriteCommand;
import com.study.exception.PathNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/boards/free/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 100MB
)
public class BoardControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Map<String, HttpCommand> commandMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        commandMap.put("GET:list", new ListCommand());
        commandMap.put("GET:views", new ReadCommand());
        commandMap.put("POST:views", new ReadCommand());
        commandMap.put("GET:write", new WriteCommand());
        commandMap.put("POST:write", new WriteCommand());

        // TODO: GET:views 추가
        // TODO: GET:write 추가
        // TODO: POST:write 추가
        // TODO: GET:modify 추가
        // TODO: POST:modify 추가

        // commandMap.put("GET:insert", new WriteCommand());
        // commandMap.put("POST:insert", new WriteCommand());
        // commandMap.put("GET:update", new UpdateCommand());
        // commandMap.put("PUT:update", new UpdateCommand());
        // commandMap.put("DELETE:delete", new DeleteCommand());
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

    private HttpCommand findTargetService(HttpServletRequest request) throws PathNotFoundException {
        String method = request.getMethod();
        String path = request.getPathInfo();

        if (!path.contains("/"))
            throw new PathNotFoundException("경로가 아닙니다.");
        String controllerString = path.split("/")[1];

        System.out.println("method: " + method); // FIXME
        System.out.println("controllerString: " + controllerString); // FIXME

        String key = method + ":" + controllerString;
        System.out.println("key : " + key);
        return commandMap.get(key);
    }

    private void handleView(HttpServletRequest request, HttpServletResponse response, String view)
            throws ServletException, IOException {

        if (view.startsWith("redirect:")) {

            String redirectPath = view.substring(9);
            // 절대 경로로 리다이렉트
            response.sendRedirect(request.getContextPath() + redirectPath);

        } else if (view.startsWith("dispatch:")) {
            request.getRequestDispatcher("/WEB-INF/views/board/free/" + view.substring(9))
                    .forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid view type");
        }
    }
}

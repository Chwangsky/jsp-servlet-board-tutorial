package com.study.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HttpCommand {
    String doService(HttpServletRequest request, HttpServletResponse response) throws Exception;
}

package com.study.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import com.study.entity.FileDownloadEntity;
import com.study.mapper.DownloadMapper;
import com.study.util.MyBatisUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/download.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class FileDownloadController extends HttpServlet {

    private SqlSessionFactory sqlSessionFactory;

    public FileDownloadController() {
        this.sqlSessionFactory = MyBatisUtil.getSqlSessionFactory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String stringId = request.getParameter("id");
        int id = 0;

        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file ID");
            return;
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            DownloadMapper mapper = session.getMapper(DownloadMapper.class);

            FileDownloadEntity fileDownloadEntity = mapper.selectFileById(id);
            if (fileDownloadEntity == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
                return;
            }

            String filePath = fileDownloadEntity.getFileDir();
            String attachType = fileDownloadEntity.getAttachType();
            String uuidFileName = fileDownloadEntity.getUuidName();

            File downloadFile = new File(filePath);

            if (!downloadFile.exists() || !downloadFile.isFile()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
                return;
            }

            if (attachType == null || attachType.isEmpty()) {
                attachType = "application/octet-stream";
            }
            response.setContentType(attachType);

            String encodedFileName =
                    URLEncoder.encode(uuidFileName, StandardCharsets.UTF_8.toString())
                            .replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + encodedFileName + "\"");

            try (FileInputStream fileInputStream = new FileInputStream(downloadFile);
                    OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error processing file download");
        }
    }
}

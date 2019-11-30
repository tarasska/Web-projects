package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String[] filesURI = uri.split("\\+");
        if (isAllFilesExist(filesURI, Paths.get("./src/main/webapp/static"))) {
            setResponse(response, Paths.get("./src/main/webapp/static"), filesURI);
        } else if (isAllFilesExist(filesURI, Paths.get(getServletContext().getRealPath("/static")))) {
            setResponse(response, Paths.get(getServletContext().getRealPath("/static")), filesURI);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    private void setResponse(HttpServletResponse response, Path pathToStatic, String[] filesURI) throws IOException {
        response.setContentType(getContentTypeFromName(filesURI[0]));
        OutputStream outputStream = response.getOutputStream();
        for (String currentFileURI : filesURI) {
            Files.copy(
                    Paths.get(pathToStatic.toString(), currentFileURI),
                    outputStream
            );
        }
        outputStream.flush();
    }

    private boolean isAllFilesExist(String[] filesURI, Path pathToStatic) {
        for (String currentFileURI : filesURI) {
            if (!new File(pathToStatic.toString(), currentFileURI).isFile()) {
                return false;
            }
        }
        return true;
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}

package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageServlet extends HttpServlet {

    private List<Pair> userMessageList = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        switch (uri) {
            case "/message/auth": {
                if (session.getAttribute("user") != null) {
                    createJson(session.getAttribute("user"), response);
                    break;
                }
                String user = request.getParameter("user");
                if (user != null) {
                    session.setAttribute("user", user);
                } else {
                    user = "";
                }
                createJson(user, response);
                break;
            }

            case "/message/findAll": {
                createJson(userMessageList, response);
                break;
            }

            case "/message/add": {
                userMessageList.add(new Pair((String) session.getAttribute("user"),
                        request.getParameter("text")));
                break;
            }

            default: {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        response.setContentType("application/json");
    }

    private void createJson(Object objectToConvert, HttpServletResponse response) throws IOException {
        String json = new Gson().toJson(objectToConvert);
        response.getWriter().print(json);
        response.getWriter().flush();
    }

    private static class Pair {
        String user;
        String text;

        Pair(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }
}

//package ru.itmo.wp.servlet;
//
//import ru.itmo.wp.util.ImageUtils;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.File;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Arrays;
//import java.util.Base64;
//import java.util.Random;
//
//public class CaptchaFilter extends HttpFilter {
//    @Override
//    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        String method = request.getMethod();
//        HttpSession session = request.getSession();
//        if ("passed".equals(session.getAttribute("captcha-status"))) {
//            chain.doFilter(request, response);
//            return;
//        }
//        if ("GET".equals(method)) {
//            response.setContentType("text/html");
//            String captchaCode = String.valueOf(new Random().nextInt(899) + 100);
//            session.setAttribute("captcha-code", captchaCode);
//            byte[] imgData = ImageUtils.toPng(captchaCode);
//            byte[] captchaData = Files.readAllBytes(Paths.get(getServletContext().getRealPath("/static/captcha.html")));
//            String captchaHtml = String.format(new String(captchaData, StandardCharsets.UTF_8.name()),
//                    Base64.getEncoder().encodeToString(imgData)
//            );
//            PrintWriter writer = response.getWriter();
//            writer.write(captchaHtml);
//            writer.flush();
//        } else if ("POST".equals(method)) {
//            if (request.getParameter("captcha-code") != null &&
//                    request.getParameter("captcha-code").equals(session.getAttribute("captcha-code"))) {
//                session.setAttribute("captcha-status", "passed");
//            }
//            response.sendRedirect(request.getRequestURI());
//        }
//    }
//}

package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class NoticePage extends Page {
    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice")
    public String noticeForm() {
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String postNotice(HttpSession httpSession, HttpServletRequest httpServletRequest) {
        noticeService.save(httpServletRequest.getParameter("noticeContent"));

        putMessage(httpSession, "Notice successfully published");
        return "redirect:/notice";
    }
}

package com.example.board.advice;

import com.example.board.exception.CommonException;
import com.example.board.exception.DBException;
import com.example.board.exception.PageNumException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ControllerAdviceMVC {
    // /board?pageNum=-2 --> throw new PageNumException(msg)
    @ExceptionHandler(PageNumException.class)
    public String exceptionHandler(PageNumException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", e.getMessage());
        return "redirect:/board?pageNum=1";
    }

    @ExceptionHandler(DBException.class)
    public String exceptionDBHandler(DBException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", "삭제 실패^_^");
        return "redirect:/";

    }

    @ExceptionHandler(CommonException.class)
    public String exceptionCommonHandler(CommonException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("msg", e.getMessage());
        return "redirect:/";

    }
}

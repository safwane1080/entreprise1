package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.exception.InvalidOrderStatusException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidOrderStatusException.class)
    public String handleInvalidStatus(
            InvalidOrderStatusException ex,
            RedirectAttributes ra) {

        ra.addFlashAttribute("error", ex.getMessage());
        return "redirect:/orders";
    }
}

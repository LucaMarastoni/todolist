package com.example.todolist.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller dedicato alla pagina di login.
 * Controlla se l'utente è già autenticato per evitare di mostrare di nuovo il form.
 */
@Controller
public class LoginController {

    /**
     * Mostra il template di login se serve, altrimenti reindirizza alla home.
     */
    @GetMapping("/login")
    public String loginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Se l'utente è già loggato non ha senso rivedere la pagina di login.
        if (authentication != null
            && authentication.isAuthenticated()
            && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        return "login";
    }
}

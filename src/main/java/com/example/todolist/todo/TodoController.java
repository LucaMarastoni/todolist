package com.example.todolist.todo; // Package del controller ToDo.

import jakarta.validation.Valid; // Import per attivare la validazione @Valid.

import org.springframework.stereotype.Controller; // Annotazione controller MVC.
import org.springframework.ui.Model; // Permette di passare dati alla view.
import org.springframework.validation.BindingResult; // Contiene gli errori di validazione.
import org.springframework.web.bind.annotation.GetMapping; // Gestisce richieste GET.
import org.springframework.web.bind.annotation.ModelAttribute; // Recupera oggetti dal model/form.
import org.springframework.web.bind.annotation.PathVariable; // Legge parametri path come l'ID.
import org.springframework.web.bind.annotation.PostMapping; // Gestisce richieste POST.
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Supporto per flash attributes sul redirect.

/**
 * Controller principale: coordina la pagina HTML con il servizio.
 * Tutto il codice è volutamente lineare e commentato.
 */
@Controller
public class TodoController {

    private final TodoService service; // Servizio che contiene la logica business.

    public TodoController(TodoService service) {
        // Spring inietta automaticamente la dipendenza.
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Se arriviamo da un redirect senza form, ne creiamo uno vuoto.
        if (!model.containsAttribute("todoForm")) {
            model.addAttribute("todoForm", new TodoForm()); // Form vuoto per la pagina.
        }
        // Popoliamo i dati necessari alla vista.
        model.addAttribute("todos", service.findAll()); // Lista di tutte le attività.
        model.addAttribute("activeCount", service.countActive()); // Numero attività attive.
        model.addAttribute("completedCount", service.countCompleted()); // Numero attività completate.
        return "index"; // Render della pagina principale.
    }

    @PostMapping("/todos")
    public String createTodo(@Valid @ModelAttribute("todoForm") TodoForm todoForm,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        // Se validazione fallisce, rimandiamo alla home con gli errori.
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.todoForm", bindingResult);
            redirectAttributes.addFlashAttribute("todoForm", todoForm);
            return "redirect:/";
        }
        // Se tutto ok delego la creazione al servizio.
        service.add(todoForm.getTitle());
        return "redirect:/"; // Redirect per evitare il doppio invio del form.
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@PathVariable Long id) {
        service.toggle(id); // Invertiamo lo stato dell'attività.
        return "redirect:/"; // Torniamo alla lista aggiornata.
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        service.delete(id); // Rimuoviamo definitivamente l'attività.
        return "redirect:/"; // Visualizziamo la pagina aggiornata.
    }

    @PostMapping("/todos/clear-completed")
    public String clearCompleted() {
        service.clearCompleted(); // Cancella tutte le attività completate.
        return "redirect:/"; // Redirect per mostrare la lista pulita.
    }
}

package com.example.spring_boot_3_todo_application.controllers;


import com.example.spring_boot_3_todo_application.modules.TodoItem;
import com.example.spring_boot_3_todo_application.services.TodoItemService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class TodoFromController {

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping("/create-todo")
    public String showCreateForm(TodoItem todoItem) {
        return "new-todo-item";
    }

    @PostMapping("/todo")
    public String createTodoItem(@Valid TodoItem todoItem, BindingResult bindingResult, Model model) {

        TodoItem item= new TodoItem();
        item.setDescription(todoItem.getDescription());
        item.setIsComplete(todoItem.getIsComplete());

        todoItemService.save(todoItem);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id, Model model) {
        TodoItem todoItem= todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalCallerException("TodoItem id: " + id + "Not Found"));
        todoItemService.delete(todoItem);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        TodoItem todoItem= todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalCallerException("TodoItem id: " + id + "Not Found"));
        model.addAttribute("todo", todoItem);
        return "edit-todo-item";
    }

    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") Long id,@Valid TodoItem todoItem, BindingResult bindingResult, Model model) {
        TodoItem item= todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalCallerException("TodoItem id: " + id + "Not Found"));
        item.setIsComplete(todoItem.getIsComplete());
        item.setDescription(todoItem.getDescription());
        todoItemService.save(item);
        return "redirect:/";
    }
}


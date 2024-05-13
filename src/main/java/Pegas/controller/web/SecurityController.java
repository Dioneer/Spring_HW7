package Pegas.controller.web;

import Pegas.dto.CreateUpdateSecurityDto;
import Pegas.entity.Role;
import Pegas.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityController {
    private final SecurityService service;

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") CreateUpdateSecurityDto user){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "registration";
    }

    @GetMapping
    public String findAll(Model model){
        model.addAttribute("users", service.findAll());
        return "users";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") Long id){
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", service.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return "user";
    }

    @PostMapping
    @Transactional
    public String create(@ModelAttribute CreateUpdateSecurityDto user){
        service.create(user);
        return "redirect:/users";
    }
    @PostMapping("/{id}/update")
    @Transactional
    public String update(@PathVariable("id") Long id, @ModelAttribute CreateUpdateSecurityDto user){
        service.update(id, user).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")
    @Transactional
    public String delete(@PathVariable("id") Long id){
        if(!service.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }

}

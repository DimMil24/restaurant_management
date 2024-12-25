package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.requests.TagDTO;
import com.dimitris.restaurant_management.services.TagService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/tag")
@Controller
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("Tags",tagService.findAll());
        return "tag/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("tag", new TagDTO());
        return "tag/create";
    }

    @PostMapping("/create")
    public String createTag(@Valid @ModelAttribute("tag") TagDTO tag) {
        tagService.createNewTag(tag.getName());
        return "redirect:/tag";
    }
}

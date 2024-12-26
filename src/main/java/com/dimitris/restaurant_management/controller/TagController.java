package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.requests.TagDTO;
import com.dimitris.restaurant_management.services.TagService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        if (tagService.getTagByName(tag.getName()).isPresent()) {
            return "redirect:/tag/create?duplicate=true";
        }
        tagService.createNewTag(tag.getName());
        return "redirect:/tag";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        tagService.deleteTag(id);
        return "redirect:/tag";
    }
}

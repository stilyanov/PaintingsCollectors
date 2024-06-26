package com.paintingscollectors.controller;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.AddPaintingDTO;
import com.paintingscollectors.model.entity.StyleEnum;
import com.paintingscollectors.service.PaintingService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/painting")
public class PaintingController {

    private final UserSession userSession;
    private final PaintingService paintingService;

    public PaintingController(UserSession userSession, PaintingService paintingService) {
        this.userSession = userSession;
        this.paintingService = paintingService;
    }

    @ModelAttribute("paintingData")
    public AddPaintingDTO paintingDTO() {
        return new AddPaintingDTO();
    }

    @ModelAttribute("styles")
    public StyleEnum[] styles() {
        return StyleEnum.values();
    }

    @GetMapping("/add")
    public String addPainting() {

        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        return "add-painting";
    }

    @PostMapping("/add")
    public String doAddPainting(@Valid AddPaintingDTO paintingDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("paintingData", paintingDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.paintingData", bindingResult);

            return "redirect:/painting/add";
        }

        boolean success = this.paintingService.addPainting(paintingDTO);

        if (!success) {
            redirectAttributes.addFlashAttribute("paintingData", paintingDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.paintingData", bindingResult);

            return "redirect:/painting/add";
        }

        return "redirect:/home";
    }

    @GetMapping("/add-to-favourites/{paintingId}")
    public String addToFavourite(@PathVariable Long paintingId){
        if (!userSession.isLoggedIn()) {
            return "redirect:home";
        }

        this.paintingService.addPaintingToFavourite(paintingId, userSession.id());

        return "redirect:/home";
    }

    @GetMapping("/remove-from-favourites/{paintingId}")
    public String removeFromFavourite(@PathVariable Long paintingId){
        if (!userSession.isLoggedIn()) {
            return "redirect:home";
        }

        this.paintingService.removePaintFromFavourites(paintingId);

        return "redirect:/home";
    }

    @GetMapping("/delete/{paintingId}")
    public String deletePainting(@PathVariable Long paintingId) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/home";
        }

        this.paintingService.deletePainting(paintingId, userSession.id());

        return "redirect:/home";

    }

    @GetMapping("/vote/{paintingId}")
    public String vote(@PathVariable Long paintingId) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/home";
        }

        this.paintingService.votePainting(paintingId, userSession.id());

        return "redirect:/home";
    }

}

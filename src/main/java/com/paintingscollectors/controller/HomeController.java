package com.paintingscollectors.controller;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.PaintingDTO;
import com.paintingscollectors.service.PaintingService;
import com.paintingscollectors.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;


@Controller
public class HomeController {

    private final UserSession userSession;
    private final PaintingService paintingService;
    private final UserService userService;

    public HomeController(UserSession userSession, PaintingService paintingService, UserService userService) {
        this.userSession = userSession;
        this.paintingService = paintingService;
        this.userService = userService;
    }


    @GetMapping("/")
    public String nonLoggedIndex() {
        if (userSession.isLoggedIn()) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String loggedIndex(Model model) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/";
        }

        List<PaintingDTO> userPaintings = this.paintingService.getPaintingsByUser();
        List<PaintingDTO> otherUsersPaintings = this.paintingService.getPaintingsByOtherUsers();
        List<PaintingDTO> userFavouritesPaintings = this.userService.findFavourites(userSession.id())
                .stream()
                .map(PaintingDTO::new)
                .toList();


        model.addAttribute("userPaintings", userPaintings);
        model.addAttribute("otherUsersPaintings", otherUsersPaintings);
        model.addAttribute("userFavouritesPaintings", userFavouritesPaintings);


        return "home";
    }

}

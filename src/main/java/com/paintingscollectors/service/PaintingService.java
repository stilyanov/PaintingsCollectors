package com.paintingscollectors.service;

import com.paintingscollectors.config.UserSession;
import com.paintingscollectors.model.dto.AddPaintingDTO;
import com.paintingscollectors.model.dto.PaintingDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.StyleRepository;
import com.paintingscollectors.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaintingService {

    private final PaintingRepository paintingRepository;
    private final StyleRepository styleRepository;
    private final UserRepository userRepository;
    private final UserSession userSession;

    public PaintingService(PaintingRepository paintingRepository, StyleRepository styleRepository, UserRepository userRepository, UserSession userSession) {
        this.paintingRepository = paintingRepository;
        this.styleRepository = styleRepository;
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    public boolean addPainting(AddPaintingDTO paintingDTO) {

        if (!userSession.isLoggedIn()) {
            return false;
        }

        Optional<User> userId = this.userRepository.findById(userSession.id());

        if (userId.isEmpty()) {
            return false;
        }

        Optional<Style> optionalStyle = this.styleRepository.findByName(paintingDTO.getStyle());

        if (optionalStyle.isEmpty()) {
            return false;
        }

        Painting painting = new Painting();
        painting.setName(paintingDTO.getName());
        painting.setAuthor(paintingDTO.getAuthor());
        painting.setImageUrl(paintingDTO.getImageUrl());
        painting.setStyle(optionalStyle.get());
        painting.setOwner(userId.get());

        this.paintingRepository.save(painting);

        return true;
    }

    public List<PaintingDTO> getPaintingsByUser() {
        Optional<User> userId = this.userRepository.findById(userSession.id());
        return userId.map(user -> this.paintingRepository.findAllByOwnerId(user.getId())
                .stream()
                .map(PaintingDTO::new)
                .toList()).orElseGet(ArrayList::new);

    }

    public List<PaintingDTO> getPaintingsByOtherUsers() {
        return this.paintingRepository
                .findAllByOwnerNot(userSession.id())
                .stream()
                .map(PaintingDTO::new)
                .toList();
    }

    public void deletePainting(Long paintingId, Long userId) {
        this.paintingRepository.deletePaintingByIdAndOwnerId(paintingId, userSession.id());
    }

    @Transactional
    public void addPaintingToFavourite(Long paintingId, Long userId) {
        Painting painting = this.paintingRepository.findById(paintingId).orElse(null);

        if (painting != null) {
            painting.setFavourite(true);
        }

        Optional<User> optionalUser = this.userRepository.findById(userId);

        if (optionalUser.get().getFavouritePaintings().contains(painting)) {
            return;
        }

        optionalUser.get().getFavouritePaintings().add(painting);

        this.userRepository.save(optionalUser.get());

    }

    @Transactional
    public void removePaintFromFavourites(Long paintingId) {
        Painting painting = this.paintingRepository.findById(paintingId).get();

        Optional<User> optionalUser = this.userRepository.findById(userSession.id());
        painting.setFavourite(false);

        optionalUser.get().getFavouritePaintings().remove(painting);

    }

    public void votePainting(Long paintingId, Long userId) {
        Painting painting = this.paintingRepository.findById(paintingId).orElse(null);

        Optional<User> optionalUser = this.userRepository.findById(userSession.id());

        if (painting.getOwner().equals(optionalUser.get().getId())) {
            return;
        }

        if (optionalUser.get().getFavouritePaintings().contains(painting)) {
            return;
        }


        painting.setVotes(painting.getVotes() + 1);

        optionalUser.get().getRatedPaintings().add(painting);


        this.userRepository.save(optionalUser.get());


    }
}

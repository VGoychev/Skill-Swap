package com.skillswapplatform.offer.service;

import com.skillswapplatform.offer.model.Offer;
import com.skillswapplatform.offer.model.OfferStatus;
import com.skillswapplatform.offer.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<Offer> getAllActiveOffers() {
        return offerRepository.findByStatusOrderByCreatedAt(OfferStatus.ACTIVE);
    }

    public List<Offer> getRecentOffers() {
        return offerRepository.findByStatusOrderByCreatedAtDesc(OfferStatus.ACTIVE);
    }

    public Optional<Offer> getOfferById(UUID id) {
        return offerRepository.findById(id);
    }

    public Offer saveOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public void deleteOffer(UUID id) {
        offerRepository.deleteById(id);
    }

    public List<Offer> getOffersByOwner(UUID ownerId) {
        return offerRepository.findByOwner_Id(ownerId);
    }
}
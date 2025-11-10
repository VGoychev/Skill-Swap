package com.skillswapplatform.offer.repository;

import com.skillswapplatform.offer.model.Offer;
import com.skillswapplatform.offer.model.OfferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {

    List<Offer> findByStatusOrderByCreatedAt(OfferStatus status);

    List<Offer> findByStatus(OfferStatus status);

    List<Offer> findByOwner_Id(UUID ownerId);

    List<Offer> findByStatusOrderByCreatedAtDesc(OfferStatus status);
}

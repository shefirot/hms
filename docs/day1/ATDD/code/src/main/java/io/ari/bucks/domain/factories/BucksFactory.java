package io.ari.bucks.domain.factories;


import io.ari.bucks.domain.Bucks;
import io.ari.bucks.domain.repositories.BucksRepository;
import io.ari.cards.domain.factories.CardsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BucksFactory {

    public Bucks createBucks(String customerId, String idCard, String mobilePhone, String customerName, String customerLastName) {
        seq++;
        String agreementId = seq.toString();
        Bucks newBucks = new Bucks(customerId, seq.toString());
        return bucksRepository.save(newBucks);
    }

    private Integer seq = 0;

    @Autowired
    private BucksRepository bucksRepository;

}
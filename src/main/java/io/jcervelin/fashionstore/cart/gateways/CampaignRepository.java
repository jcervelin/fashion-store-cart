package io.jcervelin.fashionstore.cart.gateways;

import io.jcervelin.fashionstore.cart.domains.CampaignAttributes;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public interface CampaignRepository extends MongoRepository<CampaignAttributes, ObjectId> {

    @Query("{ 'starts' : { $lte: ?0}, 'expires' : { $gt: ?0} }")
    Optional<Collection<CampaignAttributes>> findValidCampaigns(final LocalDateTime localDateTime);

}

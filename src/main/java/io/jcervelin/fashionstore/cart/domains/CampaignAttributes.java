package io.jcervelin.fashionstore.cart.domains;


import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


/**
 * This class is a perfect match for a Mongo collection domain
 * it has several parameter that can be used to parameterize dynamic offers.
 * Using MongoDB NoSql we don't have to worry in make an ALTER TABLE, create temporary table etc.
 * There is no problem if a document has more or less parameters then other.
 *
 * A valid campaign is defined when the current day is between starts and expires dates.
 */
@Data
@Builder
@Document(collection="campaigns")
@CompoundIndex(name = "find_by_dates", def = "{'starts':1,'expires':1}")
public class CampaignAttributes {
    @Id
    private ObjectId id;
    private String campaignType;
    private LocalDateTime starts;
    private LocalDateTime expires;
    private Integer percentFactor;
    private Integer buyX;
    private Integer toY;
    private List<Product> productsAffected;
    private Product promotionalProduct;
}

package io.jcervelin.fashionstore.cart.domains;


import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

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
}

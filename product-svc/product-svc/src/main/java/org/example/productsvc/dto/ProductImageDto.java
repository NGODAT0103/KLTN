package org.example.productsvc.dto;
import lombok.Data;
import java.util.UUID;



@Data
public class ProductImageDto {
    private UUID productUuid;
    private String imageUrl;
}
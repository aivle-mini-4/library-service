package aivle.infra;

import lombok.Data;

@Data
public class ImageRequest {
    private String model = "dall-e-3";
    private String prompt;
    private int n = 1;
    private String size = "1024x1024";
}

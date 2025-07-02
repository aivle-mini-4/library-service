package aivle.infra;

import lombok.Data;

@Data
public class ImageResponse {
    private ImageData[] data;

    @Data
    public static class ImageData {
        private String url;
    }
}
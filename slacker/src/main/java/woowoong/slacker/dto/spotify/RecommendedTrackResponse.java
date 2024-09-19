package woowoong.slacker.dto.spotify;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecommendedTrackResponse {
    private String artistName;
    private String artistID;
    private String trackName;
    private String trackID;
    private String imageUrl;
    private String previewUrl;
}

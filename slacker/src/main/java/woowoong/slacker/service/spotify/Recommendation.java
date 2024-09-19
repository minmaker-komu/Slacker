package woowoong.slacker.service.spotify;

import com.neovisionaries.i18n.CountryCode;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsTopTracksRequest;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;
import woowoong.slacker.dto.spotify.RecommendedTrackResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

@Service
public class Recommendation {
    SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(CreateToken.accessToken())
            .build();


    // 아티스트 이름으로 ID 가져오기
    public String getArtistIdByName(String artistName) throws Exception {
        SearchArtistsRequest searchRequest = spotifyApi.searchArtists(artistName).build();
        Artist[] artists = searchRequest.execute().getItems();

        if (artists.length > 0) {
            return artists[0].getId(); // 첫 번째 아티스트의 ID 반환
        } else {
            throw new Exception("No artist found with name: " + artistName);
        }
    }

    // 아티스트 Top Tracks 상위 5개 가져오기
    public List<String> getTopTracksByArtistId(String artistId) throws Exception {

        GetArtistsTopTracksRequest topTracksRequest = spotifyApi
                .getArtistsTopTracks(artistId, CountryCode.getByLocale(Locale.KOREA))
                .build();

        Track[] tracks = topTracksRequest.execute();
        List<String> topTrackIds = new ArrayList<>();

        for (int i = 0; i < 4; i++) {  // 상위 4개 가져오기
            topTrackIds.add(tracks[i].getId());
        }


        return topTrackIds;
    }

    // 추천곡 받아오는 메서드
    public List<RecommendedTrackResponse> getRecommendations(String artistName) throws Exception {
        // 추천받은 곡을 DTO 리스트로 반환
        List<RecommendedTrackResponse> recommendedTracksDtoList = new ArrayList<>();

        try {
            // 1. 아티스트 이름으로 아티스트 ID 가져오기
            String artistId = getArtistIdByName(artistName);

            // 2. 해당 아티스트의 상위 5개의 Top Tracks ID 가져오기
            List<String> topTrackIds = getTopTracksByArtistId(artistId);

            // StringJoiner로 트랙 ID들을 하나의 콤마로 구분된 String으로 변환
            StringJoiner trackIdsJoiner = new StringJoiner(",");
            for (String trackId : topTrackIds) {
                trackIdsJoiner.add(trackId);
            }
            String seedTracks = trackIdsJoiner.toString(); // 콤마로 구분된 트랙 ID들

            // 3. Recommendations API 호출
            GetRecommendationsRequest recommendationsRequest;

            recommendationsRequest = spotifyApi.getRecommendations()
                    .seed_artists(artistId)
                    .seed_tracks(seedTracks)
                    .limit(10)
                    .build();

            Recommendations recommendations = recommendationsRequest.execute();
            Track[] recommendedTracks = recommendations.getTracks();

            for (Track track : recommendedTracks) {

                String recommendedArtistId = track.getArtists()[0].getId();
                String recommendedArtistName = track.getArtists()[0].getName();
                String trackId = track.getId();
                String trackName = track.getName();
                String previewUrl = track.getPreviewUrl();

                Image[] images = track.getAlbum().getImages();
                String imageUrl = (images.length > 0) ? images[0].getUrl() : "NO_IMAGE";

                recommendedTracksDtoList.add(
                        RecommendedTrackResponse.builder()
                                .trackID(trackId)
                                .trackName(trackName)
                                .artistID(recommendedArtistId)
                                .artistName(recommendedArtistName)
                                .previewUrl(previewUrl)
                                .imageUrl(imageUrl)
                                .build()
                );
            }

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return recommendedTracksDtoList;
    }
}


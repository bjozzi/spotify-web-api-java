package com.wrapper.spotify.methods;

import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.JsonUtil;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.models.AudioFeature;
import net.sf.json.JSONObject;

import java.io.IOException;

public class AudioFeatureRequest extends AbstractRequest {


    public AudioFeatureRequest(Builder builder) {
        super(builder);
    }

    public SettableFuture<AudioFeature> getAsync() {
        SettableFuture<AudioFeature> audioFeatureFuture = SettableFuture.create();

        try {
            String jsonString = getJson();
            audioFeatureFuture.set(JsonUtil.createAudioFeature(JSONObject.fromObject(jsonString)));
        } catch (Exception e) {
            audioFeatureFuture.setException(e);
        }

        return audioFeatureFuture;
    }

    public AudioFeature get() throws IOException, WebApiException {
        String jsonString = getJson();
        JSONObject jsonObject = JSONObject.fromObject(jsonString);

        return JsonUtil.createAudioFeature(jsonObject);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends AbstractRequest.Builder<Builder> {

        /**
         * The audio request with the given song id.
         *
         * @param id The id for the song.
         * @return AlbumRequest
         */
        public Builder id(String id) {
            assert (id != null);
            return path(String.format("/v1/audio-features/%s", id));
        }

        public AudioFeatureRequest build() {
            return new AudioFeatureRequest(this);
        }

    }
}

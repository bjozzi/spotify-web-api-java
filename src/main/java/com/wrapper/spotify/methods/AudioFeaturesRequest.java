package com.wrapper.spotify.methods;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.JsonUtil;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.models.AudioFeature;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class AudioFeaturesRequest extends AbstractRequest {


    public AudioFeaturesRequest(Builder builder) {
        super(builder);
    }

    public SettableFuture<List<AudioFeature>> getAsync() {
        final SettableFuture<List<AudioFeature>> audioFeatureFuture = SettableFuture.create();

        try {
            final String jsonString = getJson();
            audioFeatureFuture.set(JsonUtil.createAudioFeatures(JSONObject.fromObject(jsonString)));
        } catch (final Exception e) {
            audioFeatureFuture.setException(e);
        }

        return audioFeatureFuture;
    }

    public List<AudioFeature> get() throws IOException, WebApiException {
        final String jsonString = getJson();
        final JSONObject jsonObject = JSONObject.fromObject(jsonString);

        return JsonUtil.createAudioFeatures(jsonObject);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends AbstractRequest.Builder<Builder> {

        /**
         * The audio request with the given song id.
         *
         * @param ids The id for the song.
         * @return AlbumRequest
         */
        public Builder id(String ...ids) {
            assert (ids != null);
            final String idsParameter = Joiner.on(",").join(ids);
            path("/v1/audio-features");
            return parameter("ids", idsParameter);
        }

        public AudioFeaturesRequest build() {
            return new AudioFeaturesRequest(this);
        }

    }
}

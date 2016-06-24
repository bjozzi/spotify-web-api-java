package com.wrapper.spotify.methods;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.TestUtil;
import com.wrapper.spotify.models.AudioFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AudioFeatureRequestTest {
    @Test
    public void shouldGetAudioFeatureResult_async() throws Exception {
        final Api api = Api.DEFAULT_API;

        final AudioFeatureRequest request = api.getAudioFeature("06AKEBrKUckW0KREUWRnvT")
                .httpManager(TestUtil.MockedHttpManager.returningJson("audio-feature.json"))
                .build();

        final CountDownLatch asyncCompleted = new CountDownLatch(1);

        final SettableFuture<AudioFeature> future = request.getAsync();

        Futures.addCallback(future, new FutureCallback<AudioFeature>() {
            @Override
            public void onSuccess(AudioFeature audioFeature) {
                assertNotNull(audioFeature);
                assertEquals("06AKEBrKUckW0KREUWRnvT", audioFeature.getId());

                asyncCompleted.countDown();
            }

            @Override
            public void onFailure(Throwable throwable) {
                fail("Failed to resolve future");
            }
        });

        asyncCompleted.await(1, TimeUnit.SECONDS);
    }

    @Test
    public void shouldGetAudioFeatureResult_sync() throws Exception {
        final Api api = Api.DEFAULT_API;

        final AudioFeatureRequest request = api.getAudioFeature("06AKEBrKUckW0KREUWRnvT")
                .httpManager(TestUtil.MockedHttpManager.returningJson("audio-feature.json"))
                .build();

        final AudioFeature audioFeature = request.get();

        assertNotNull(audioFeature);
        assertEquals("06AKEBrKUckW0KREUWRnvT", audioFeature.getId());
    }
}

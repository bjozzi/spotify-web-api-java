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
public class AudioFeaturesRequestTest {
    @Test
    public void shouldGetAudioFeaturesResult_async() throws Exception {
        final Api api = Api.DEFAULT_API;

        final AudioFeaturesRequest request = api.getAudioFeatures("4JpKVNYnVcJ8tuMKjAj50A","2NRANZE9UCmPAS5XVbXL40","24JygzOLM0EmRQeGtFcIcG")
                .httpManager(TestUtil.MockedHttpManager.returningJson("audio-features.json"))
                .build();

        final CountDownLatch asyncCompleted = new CountDownLatch(1);

        final SettableFuture<List<AudioFeature>> future = request.getAsync();

        Futures.addCallback(future, new FutureCallback<List<AudioFeature>>() {
            @Override
            public void onSuccess(List<AudioFeature> audioFeatures) {
                assertNotNull(audioFeatures);
                assertEquals(audioFeatures.size(),3);
                assertEquals("4JpKVNYnVcJ8tuMKjAj50A", audioFeatures.get(0).getId());
                assertEquals("2NRANZE9UCmPAS5XVbXL40", audioFeatures.get(1).getId());
                assertEquals("24JygzOLM0EmRQeGtFcIcG", audioFeatures.get(2).getId());

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
    public void shouldGetAudioFeaturesResult_sync() throws Exception {
        final Api api = Api.DEFAULT_API;

        final AudioFeaturesRequest request = api.getAudioFeatures("4JpKVNYnVcJ8tuMKjAj50A","2NRANZE9UCmPAS5XVbXL40","24JygzOLM0EmRQeGtFcIcG")
                .httpManager(TestUtil.MockedHttpManager.returningJson("audio-features.json"))
                .build();

        final List<AudioFeature> audioFeatures = request.get();

        assertNotNull(audioFeatures);
        assertEquals(audioFeatures.size(),3);
        assertEquals("4JpKVNYnVcJ8tuMKjAj50A", audioFeatures.get(0).getId());
        assertEquals("2NRANZE9UCmPAS5XVbXL40", audioFeatures.get(1).getId());
        assertEquals("24JygzOLM0EmRQeGtFcIcG", audioFeatures.get(2).getId());
    }
}

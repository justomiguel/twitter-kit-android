/*
 * Copyright (C) 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.twitter.sdk.android.core.models;

import io.fabric.sdk.android.FabricAndroidTestCase;
import io.fabric.sdk.android.services.common.CommonUtils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

public class TweetEntitiesTest extends FabricAndroidTestCase {

    private static final int EXPECTED_URLS_SIZE = 1;
    private static final int EXPECTED_USER_MENTIONS_SIZE = 1;
    private static final int EXPECTED_MEDIA_SIZE = 1;
    private static final int EXPECTED_HASHTAGS_SIZE = 1;

    private Gson gson;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gson = new Gson();
    }

    public void testConstructor_nullParameters() {
        try {
            final TweetEntities entities = new TweetEntities(null, null, null, null);
            assertEquals(Collections.EMPTY_LIST, entities.urls);
            assertEquals(Collections.EMPTY_LIST, entities.userMentions);
            assertEquals(Collections.EMPTY_LIST, entities.media);
            assertEquals(Collections.EMPTY_LIST, entities.hashtags);
        } catch (Exception e) {
            fail();
        }
    }

    public void testDeserialization() throws IOException {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new InputStreamReader(
                    getContext().getAssets().open("model_tweetentities.json")));
            final TweetEntities tweetEntities = gson.fromJson(reader, TweetEntities.class);
            // We simply assert that we parsed it successfully and rely on our other unit tests to
            // verify parsing of the individual objects.
            assertEquals(EXPECTED_URLS_SIZE, tweetEntities.urls.size());
            assertEquals(EXPECTED_USER_MENTIONS_SIZE, tweetEntities.userMentions.size());
            assertEquals(EXPECTED_MEDIA_SIZE, tweetEntities.media.size());
            assertEquals(EXPECTED_HASHTAGS_SIZE, tweetEntities.hashtags.size());
        } finally {
            CommonUtils.closeQuietly(reader);
        }
    }
}

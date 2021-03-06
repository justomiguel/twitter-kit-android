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

package com.twitter.sdk.android.core;

import android.test.AndroidTestCase;

import io.fabric.sdk.android.services.network.NetworkUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class DefaultClientTest extends AndroidTestCase {
    SSLSocketFactory sslSocketFactory;
    DefaultClient client;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sslSocketFactory = NetworkUtils.getSSLSocketFactory(
                new TwitterPinningInfoProvider(getContext()));

        client = new DefaultClient(sslSocketFactory);
    }

    public void testOpenSslConnection_https() throws NoSuchAlgorithmException,
            KeyManagementException, IOException {
        final URL httpsUrl = new URL("https://example.com");

        final HttpsURLConnection connection = (HttpsURLConnection) httpsUrl.openConnection();
        client.openSslConnection(connection);

        assertEquals(sslSocketFactory, connection.getSSLSocketFactory());
    }


    public void testOpenSslConnection_http() throws NoSuchAlgorithmException,
            KeyManagementException, IOException {
        final URL httpUrl = new URL("http://example.com");

        final HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
        client.openSslConnection(connection);

        assertFalse(connection instanceof HttpsURLConnection);
    }

    public void testOpenSslConnection_nullSslSocketFactory() throws IOException {
        final DefaultClient client = new DefaultClient(null);

        final URL httpsUrl = new URL("https://example.com");

        final HttpsURLConnection connection = (HttpsURLConnection) httpsUrl.openConnection();
        client.openSslConnection(connection);
    }
}

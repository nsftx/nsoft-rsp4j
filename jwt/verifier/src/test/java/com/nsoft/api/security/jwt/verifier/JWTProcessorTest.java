package com.nsoft.api.security.jwt.verifier;

import static com.nsoft.api.security.test_support.TestResources.THE_ALMIGHTY_KEY;
import static com.nsoft.api.security.test_support.TestResources.THE_EVERLASTING_TOKEN;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import com.nsoft.api.security.test_support.LocalProcessorConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;

final class JWTProcessorTest {

    private static ClientAndServer clientAndServer;

    @BeforeAll
    public static void setup() {
        clientAndServer = ClientAndServer.startClientAndServer(18081);
    }

    @AfterAll
    public static void tearDown() {
        clientAndServer.stop();
    }

    @Test
    void process() {
        final JWTProcessor processor = JWTProcessor.fromConfiguration(new LocalProcessorConfiguration());

        clientAndServer
                .when(request()
                        .withMethod("GET")
                        .withPath("/.well-known/jwks.json"))
                .respond(response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(THE_ALMIGHTY_KEY));

        final String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";

        assertFalse(processor.process(invalidToken).isPresent());
        assertFalse(processor.process(() -> invalidToken).isPresent());

        assertTrue(processor.process(THE_EVERLASTING_TOKEN).isPresent());
        assertTrue(processor.process(() -> THE_EVERLASTING_TOKEN).isPresent());
    }

    @Test
    void getConfiguration() {
        final JWTProcessorConfiguration configuration = new LocalProcessorConfiguration();

        assertNotNull(JWTProcessor.fromConfiguration(configuration).getConfiguration());
    }

    @Test
    void getDefault() {
        assertNotNull(JWTProcessor.fromConfiguration(new LocalProcessorConfiguration()));
    }

}

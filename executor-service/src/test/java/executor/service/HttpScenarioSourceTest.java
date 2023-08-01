package executor.service;

import executor.service.exception.okhttp.OkHttpException;
import executor.service.exception.okhttp.UnsuccessfulResponseException;
import executor.service.maintenance.HttpScenarioSource;
import executor.service.model.ScenarioDto;
import executor.service.model.StepDto;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HttpScenarioSourceTest {
     public MockWebServer mockWebServer = new MockWebServer();
     public String URL = "/api/scenarios";
     public String urlString;
     public String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.e30.t-IDcSemACt8x4iTMCda8Yhe3iZaWbvV5XKSTbuAn0M";
     public OkHttpClient okHttpClient = new OkHttpClient();

     @BeforeEach
     public void setUp() throws IOException {
          mockWebServer.start();
          urlString = mockWebServer.url(URL).toString();
     }

     @AfterEach
     public void tearDown() throws IOException {
          mockWebServer.shutdown();
     }

     @Test
     public void testGetScenarios() {
          mockWebServer.enqueue(new MockResponse().setBody(getJsonScenarios()));

          HttpScenarioSource reader = new HttpScenarioSource(urlString, token, okHttpClient);
          assertEquals(getScenariosList(), reader.getScenarios());
     }

     @Test
     public void testGetScenariosIfServerReturns500() {
          mockWebServer.enqueue(new MockResponse().setResponseCode(500));

          HttpScenarioSource reader = new HttpScenarioSource(urlString, token, okHttpClient);
          UnsuccessfulResponseException thrown = Assertions.assertThrows(
                  UnsuccessfulResponseException.class,
                  reader::getScenarios,
                  "UnsuccessfulResponseException was expected");

          Assertions.assertEquals(
                  "Request failed with status code: 500",
                  thrown.getMessage());
     }

     @Test
     public void testGetScenariosIfResponseBogyIsNull() throws IOException {
          OkHttpClient client = Mockito.mock(OkHttpClient.class);
          Call call = mock(Call.class);
          Response response = mock(Response.class);

          when(client.newCall(any(Request.class))).thenReturn(call);
          when(call.execute()).thenReturn(response);
          when(response.code()).thenReturn(200);
          when(response.body()).thenReturn(null);

          HttpScenarioSource reader = new HttpScenarioSource(urlString, token, client);

          OkHttpException thrown = Assertions.assertThrows(
                  OkHttpException.class,
                  reader::getScenarios,
                  "OkHttpException was expected");

          Assertions.assertEquals("The response body from " + urlString + " was invalid", thrown.getMessage());
     }

     @Test
     public void testGetScenariosIfUrlStringIsNull() {
          InvalidParameterException thrown = Assertions.assertThrows(
                  InvalidParameterException.class,
                  () -> new HttpScenarioSource(null, token, okHttpClient),
                  "InvalidParameterException was expected");

          Assertions.assertEquals("Scenarios url is invalid", thrown.getMessage());
     }

     @Test
     public void testGetScenariosIfTokenIsNull() {
          InvalidParameterException thrown = Assertions.assertThrows(
                  InvalidParameterException.class,
                  () -> new HttpScenarioSource(urlString, null, okHttpClient),
                  "InvalidParameterException was expected");

          Assertions.assertEquals("Token is invalid", thrown.getMessage());
     }

     public String getJsonScenarios() {
          return """
                  [
                       {
                            "name": "test scenario 1",
                            "site": "http://info.cern.ch",
                            "steps" : [
                                 {
                                      "action": "clickCss",
                                         "value": "body > ul > li:nth-child(1) > a"
                                 },
                                 {
                                      "action": "sleep",
                                         "value": "5:10"
                                 },
                                 {
                                      "action": "clickXpath",
                                         "value": "/html/body/p"
                                 }
                            ]
                       },
                       {
                            "name": "test scenario 2",
                            "site": "http://info.cern.ch",
                            "steps" : [
                                 {
                                      "action": "clickXpath",
                                         "value": "/html/body/p"
                                 },
                                 {
                                      "action": "sleep",
                                         "value": "5:10"
                                 },
                                 {
                                      "action": "clickCss",
                                         "value": "body > ul > li:nth-child(1) > a"
                                 }
                            ]
                       }
                  ]""";
     }

     public List<ScenarioDto> getScenariosList() {
          StepDto firstStepFirstScenario = new StepDto("clickCss", "body > ul > li:nth-child(1) > a");
          StepDto secondStepFirstScenario = new StepDto("sleep", "5:10");
          StepDto thirdStepFirstScenario = new StepDto("clickXpath", "/html/body/p");

          List<StepDto> stepsFirstScenario = List.of(firstStepFirstScenario, secondStepFirstScenario, thirdStepFirstScenario);
          ScenarioDto firstScenario = new ScenarioDto("test scenario 1", "http://info.cern.ch", stepsFirstScenario);

          StepDto firstStepSecondScenario = new StepDto("clickXpath", "/html/body/p");
          StepDto secondStepSecondScenario = new StepDto("sleep", "5:10");
          StepDto thirdStepSecondScenario = new StepDto("clickCss", "body > ul > li:nth-child(1) > a");

          List<StepDto> stepsSecondScenario = List.of(firstStepSecondScenario, secondStepSecondScenario, thirdStepSecondScenario);
          ScenarioDto secondScenario = new ScenarioDto("test scenario 2", "http://info.cern.ch", stepsSecondScenario);

          return List.of(firstScenario, secondScenario);
     }
}
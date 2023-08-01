package com.outfit7.fun7gamebackend.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.outfit7.fun7gamebackend.model.ServiceStatus;
import com.outfit7.fun7gamebackend.service.CustomerSupportService;
import com.outfit7.fun7gamebackend.service.MultiplayerService;
import com.outfit7.fun7gamebackend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ServicesControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private MultiplayerService multiplayerService;

    @MockBean
    private CustomerSupportService customerSupportService;

    @Test
    void testCheckServicesEndpoint() throws Exception {
        // Define the expected ServiceStatus object
        ServiceStatus expectedStatus = ServiceStatus.builder()
                .multiplayer("enabled")
                .userSupport("enabled")
                .ads("enabled")
                .build();

        // Mock the behavior of the services for this test
        when(userService.getApiCallsForUser("user123")).thenReturn(10);
        when(multiplayerService.isMultiplayerEnabled(10, "user123", "US")).thenReturn(true);
        when(customerSupportService.isCustomerSupportEnabled("Europe/Ljubljana")).thenReturn(true);

        // Perform a GET request to the /api/services endpoint
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/services")
                        .param("timezone", "Europe/Ljubljana")
                        .param("userId", "user123")
                        .param("cc", "US")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Verify the response status and content
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(toJson(expectedStatus));
    }

    // Helper method to convert an object to JSON string
    private String toJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}

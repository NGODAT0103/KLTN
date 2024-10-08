package com.example.yamp.usersvc.service;

import static org.mockito.BDDMockito.*;

import com.example.yamp.usersvc.cache.AuthSvcRepository;
import com.example.yamp.usersvc.dto.customer.CustomerRegisterDto;
import com.example.yamp.usersvc.dto.mapper.CustomerMapper;
import com.example.yamp.usersvc.dto.mapper.CustomerMapperImpl;
import com.example.yamp.usersvc.persistence.entity.Customer;
import com.example.yamp.usersvc.persistence.repository.CustomerRepository;
import com.example.yamp.usersvc.service.impl.CustomerServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.UUID;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("unit-test")
public class CustomerServiceTest {
  private CustomerService customerService;
  private final CustomerMapper customerMapper = new CustomerMapperImpl();
  private MockWebServer mockWebServer;
  private final BasicJsonTester jsonTester = new BasicJsonTester(getClass());
  private static final String username = "testUser";
  private static final String password = "password";
  private static final String email = "example@gmail.com";
  private static final String firstName = "John";
  private static final String lastName = "Doe";
  private static final UUID customerUuid = UUID.fromString("1a35d863-0cd9-4bc1-8cc4-f4cddca97721");
  private CustomerRegisterDto customerRegisterDto;
  private final ClassLoader classLoader = getClass().getClassLoader();

  @BeforeEach
  void setUp() {
    this.mockWebServer = new MockWebServer();
    CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
    AuthSvcRepository authSvcRepository = Mockito.mock(AuthSvcRepository.class);
    URI mockBaseUri = this.mockWebServer.url("/").uri();
    WebClient webClient = WebClient.builder().baseUrl(mockBaseUri.toString()).build();
    this.customerService =
        new CustomerServiceImpl(
            customerRepository, authSvcRepository, this.customerMapper, webClient);
    this.customerRegisterDto =
        CustomerRegisterDto.builder()
            .username(username)
            .firstName(firstName)
            .lastName(lastName)
            .email(email)
            .password(password)
            .build();
    Customer customerMockResponse = new Customer();
    customerMockResponse.setCustomerUuid(customerUuid);
    customerMockResponse.setFirstName(firstName);
    customerMockResponse.setLastName(lastName);
    given(customerRepository.save(any(Customer.class))).willReturn(customerMockResponse);
  }

  @Test
  @DisplayName("Register a new customer")
  void givenRegisterDto_whenRegister_thenReturnSuccessfulCustomer()
      throws IOException, InterruptedException {
    InputStream inputStream =
        classLoader.getResourceAsStream("mockresponse/accountRegisterSuccessful.json");
    Assertions.assertThat(inputStream).isNotNull();
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(201)
            .addHeader("Content-Type", "application/json")
            .setBody(new String(inputStream.readAllBytes())));

    inputStream.close();
    customerService.register(customerRegisterDto);
    RecordedRequest recordedRequest = mockWebServer.takeRequest();
    JsonContent<Object> body = jsonTester.from(recordedRequest.getBody().readUtf8());
    Assertions.assertThat(body).extractingJsonPathValue("$.username").isEqualTo(username);
    Assertions.assertThat(body).extractingJsonPathValue("$.email").isEqualTo(email);
    Assertions.assertThat(body).extractingJsonPathStringValue("$.uuid").isNotEmpty();
  }

  @Test
  @DisplayName("Register a new customer but conflict at auth-service")
  public void givenConflictRegisterAccountDto_whenRegister_thenThrowWebClientResponseException()
      throws IOException {
    InputStream inputStream =
        classLoader.getResourceAsStream("mockresponse/accountRegisterReturnConflict.json");
    assert inputStream != null;
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(409)
            .addHeader("Content-Type", "application/json")
            .setBody(new String(inputStream.readAllBytes())));

    inputStream.close();
    Assertions.assertThatThrownBy(() -> customerService.register(customerRegisterDto))
        .isInstanceOf(WebClientResponseException.class)
        .hasMessageContaining("409 Conflict");
  }
}

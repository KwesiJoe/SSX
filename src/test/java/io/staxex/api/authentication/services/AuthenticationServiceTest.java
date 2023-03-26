//package io.staxex.api.authentication.services;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.staxex.api.authentication.enums.ERole;
//import io.staxex.api.authentication.models.Role;
//import io.staxex.api.authentication.models.SecureTraderDetails;
//import io.staxex.api.authentication.models.Trader;
//import io.staxex.api.authentication.payload.request.LoginRequest;
//import io.staxex.api.authentication.payload.response.JwtResponse;
//import io.staxex.api.authentication.repositories.RoleRepository;
//import io.staxex.api.authentication.repositories.TraderRepository;
//import io.staxex.api.wallets.services.WalletService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class AuthenticationServiceTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TraderRepository traderRepository;
//
//    @MockBean
//    private RoleRepository roleRepository;
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//    @MockBean
//    private TokenService tokenService;
//
//    @MockBean
//    private AuthenticationManager authenticationManager;
//
//    @MockBean
//    private WalletService walletService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private Trader trader;
//
//    private Role role;
//
//    private LoginRequest loginRequest;
//
//    private String jwtToken;
//
//    @BeforeEach
//    void setUp() {
//        trader = new Trader();
//        trader.setId(1L);
//        trader.setFirstName("John");
//        trader.setLastName("Doe");
//        trader.setEmail("john.doe@example.com");
//        trader.setPassword("password");
//
//        role = new Role();
//        role.setId(1L);
//        role.setName(ERole.ROLE_TRADER);
//
//        loginRequest = new LoginRequest();
//        loginRequest.setEmail("john.doe@example.com");
//        loginRequest.setPassword("password");
//
//        jwtToken = "jwtToken";
//    }
//
//    @Test
//    void testCreateTrader() throws Exception {
//        // Setup
//        Mockito.when(traderRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
//        Mockito.when(roleRepository.findByName(Mockito.any(ERole.class))).thenReturn(Optional.of(role));
//        Mockito.when(traderRepository.save(Mockito.any(Trader.class))).thenReturn(trader);
//        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("password");
//
//        // Exercise
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
//                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(trader)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//
//        // Verify
//        Mockito.verify(traderRepository, Mockito.times(1)).existsByEmail(Mockito.anyString());
//        Mockito.verify(roleRepository, Mockito.times(1)).findByName(Mockito.any(ERole.class));
//        Mockito.verify(traderRepository, Mockito.times(1)).save(Mockito.any(Trader.class));
//        Mockito.verify(walletService, Mockito.times(1)).createWallet(Mockito.any(Trader.class));
//
//        String response = mvcResult.getResponse().getContentAsString();
//        JwtResponse jwtResponse = objectMapper.readValue(response, JwtResponse.class);
//
//        Assertions.assertEquals(jwtToken, jwtResponse.getToken());
//        Assertions.assertEquals(trader.getId(), jwtResponse.getId());
//        Assertions.assertEquals(trader.getFirstName(), jwtResponse.getFirstName());
//        Assertions.assertEquals(trader.getLastName(), jwtResponse.getLastName());
//        Assertions.assertEquals(trader.getEmail(), jwtResponse.getEmail());
//        Assertions.assertTrue(jwtResponse.getRoles().contains(ERole.ROLE_TRADER.name()));
//    }
//
//    @Test
//    void testCreateTraderWhenTraderExists() throws Exception {
//        // Setup
//        Mockito.when(traderRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
//
//        // Exercise
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(trader)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Trader not found"))
//                .andReturn();
//
//        // Verify
//        Mockito.verify(traderRepository, Mockito.times(1)).existsByEmail(Mockito.anyString());
//        Mockito.verifyNoMoreInteractions(traderRepository, roleRepository, passwordEncoder, tokenService, authenticationManager, walletService);
//    }
//
//    @Test
//    void testAuthenticateUser() throws Exception {
//// Setup
//        List<Role> roles = new ArrayList<>();
//        roles.add(new Role(ERole.ROLE_TRADER));
//        Trader trader = new Trader("John", "Doe", "johndoe@example.com", "password");
//        trader.setRoles(new HashSet<>(roles));
//        SecureTraderDetails traderDetails = new SecureTraderDetails(trader);
//    }
//}
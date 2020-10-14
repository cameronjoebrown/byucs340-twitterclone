package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.BeforeEach;

import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;

public class LoginServiceTest {

    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginRegisterResponse successResponse;
    private LoginRegisterResponse failureResponse;

    private LoginService LoginServiceSpy;

    /**
     * Create a LoginService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {

    }
}

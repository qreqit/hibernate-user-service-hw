package mate.academy;

import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Injector;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        AuthenticationService authService = (AuthenticationService)
                injector.getInstance(AuthenticationService.class);

        String testEmail = "test@mail.com";
        String testPassword = "123456";

        try {
            User registeredUser = authService.register(testEmail, testPassword);
            System.out.println("User registered: " + registeredUser.getEmail());
        } catch (RegistrationException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }

        try {
            User loggedInUser = authService.login(testEmail, testPassword);
            System.out.println("User logged in: " + loggedInUser.getEmail());
        } catch (AuthenticationException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }
}

package mate.academy.service.impl;

import mate.academy.dao.UserDao;
import mate.academy.exception.AuthenticationException;
import mate.academy.exception.RegistrationException;
import mate.academy.lib.Inject;
import mate.academy.model.User;
import mate.academy.service.AuthenticationService;
import mate.academy.util.HashUtil;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserDao userDao;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userDao.findByEmail(email).orElseThrow(
                () -> new AuthenticationException("User with email " + email + " does not exist")
        );

        String hashedPassword = HashUtil.hashPassword(password, user.getSalt());

        if (!hashedPassword.equals(user.getPassword())) {
            throw new AuthenticationException("Incorrect email or password");
        }

        return user;
    }

    @Override
    public User register(String email, String password) throws RegistrationException {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            throw new RegistrationException("Email or password can't be empty");
        }

        if (userDao.findByEmail(email).isPresent()) {
            throw new RegistrationException("User with email " + email + " already exists");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setSalt(HashUtil.getSalt());
        newUser.setPassword(HashUtil.hashPassword(password, newUser.getSalt()));

        userDao.add(newUser);

        return newUser;
    }
}

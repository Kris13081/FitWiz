package uni.graduate.fitwiz.service;

import org.springframework.stereotype.Service;
import uni.graduate.fitwiz.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
}

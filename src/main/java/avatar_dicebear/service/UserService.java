package avatar_dicebear.service;

import avatar_dicebear.entity.User;
import avatar_dicebear.exception.EntityNotFoundException;
import avatar_dicebear.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Value("${dicebear.api.base_url}")
    private String diceBearBaseUrl;

    public User createAvatar(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        String avatarUrl = generateRandomAvatarUrl();
        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);

        return user;
    }

    public String generateRandomAvatarUrl() {
        String uniqueFilename = UUID.randomUUID().toString();
        return diceBearBaseUrl + uniqueFilename;
    }
}
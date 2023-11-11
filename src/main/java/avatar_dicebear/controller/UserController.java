package avatar_dicebear.controller;

import avatar_dicebear.entity.User;
import avatar_dicebear.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PutMapping("/{userId}/createAvatar")
    public User createAvatar(@PathVariable Long userId) {
        User user = userService.createAvatar(userId);
        log.info("Avatar created for user with ID: {}", userId);
        return user;
    }

    @GetMapping("/getRandomAvatar")
    public String generateRandomAvatarUrl() {
        String avatarUrl = userService.generateRandomAvatarUrl();
        log.info("Generated random avatar URL: {}", avatarUrl);
        return avatarUrl;
    }
}
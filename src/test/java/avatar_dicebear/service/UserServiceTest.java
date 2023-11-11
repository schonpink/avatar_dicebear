package avatar_dicebear.service;

import avatar_dicebear.entity.User;
import avatar_dicebear.exception.EntityNotFoundException;
import avatar_dicebear.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private User user;

    @BeforeEach
    void setUp() {
        user = mock(User.class);

        ReflectionTestUtils.setField(userService, "diceBearBaseUrl", "https://api.dicebear.com/7.x/adventurer/svg?seed=");
    }

    @Test
    public void testCreateAvatar() {
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.createAvatar(userId);

        assertNotNull(result.getAvatarUrl());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testCreateAvatar_UserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.createAvatar(userId));
    }

    @Test
    public void testGenerateRandomAvatarUrl() {
        String avatarUrl = userService.generateRandomAvatarUrl();

        assertTrue(avatarUrl.startsWith("https://api.dicebear.com/7.x/adventurer/svg?seed="));
    }

}
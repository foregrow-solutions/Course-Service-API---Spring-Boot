package com.winggs.course.security;

import com.winggs.course.modal.entity.User;
import com.winggs.course.modal.enums.Role;
import com.winggs.course.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class SchoolSecurity {
    private final UserRepo userRepo;

    @Transactional(readOnly = true)
    public boolean hasUserEditPermission(@CurrentUser AuthenticatedUser authenticatedUser) {
        User user = userRepo.getReferenceById(authenticatedUser.getUsername());
        return user.getRole() != null && ((user.getRole() == Role.ADMIN || user.getRole() == Role.SCHOOL_ADMIN) || (user.getRole() == Role.SCHOOL_FACILITY));
    }
}

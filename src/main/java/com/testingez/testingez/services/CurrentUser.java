package com.testingez.testingez.services;

import com.testingez.testingez.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@SessionScope
public class CurrentUser {

    private User user;

    public String getUsername() {
        return user.getUsername();
    }

    public boolean isLogged() {
        return user != null;
    }

}

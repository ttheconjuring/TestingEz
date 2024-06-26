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
@NoArgsConstructor
@Component
@SessionScope
public class CurrentUser {

    private User user;

    public String getUsername() {
        return this.user.getUsername();
    }

    public Long getId() {
        return this.user.getId();
    }

    public boolean isLogged() {
        return this.user != null;
    }

}

package ru.itmo.wp.form;

import javax.validation.constraints.NotNull;

public class DisabledForm {
    @NotNull
    private Long userId;

    private boolean disabled;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

}

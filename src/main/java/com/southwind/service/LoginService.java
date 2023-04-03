package com.southwind.service;

import com.southwind.form.LoginForm;
import com.southwind.result.LoginResult;

public interface LoginService {
    public LoginResult login(LoginForm loginForm);
}

package com.novaq.leatherfactory.util

import java.util.regex.Pattern

class PasswordUtil {

    fun isPasswordValid(password: String): Boolean {
        //val regex = "^(?=.*\\d)(?=.*[~`!@#$%^&*()-])(?=.*[a-zA-Z]).{8,30}$" //8,30 자리수 선정, 영문,숫자,특수문자 포함 여부
        val regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\$@!%*#?&])[A-Za-z\\d\$@!%*#?&]{8,30}"
        return Pattern.matches(regex, password)
    }
}
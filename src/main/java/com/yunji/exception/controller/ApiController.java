package com.yunji.exception.controller;


import com.yunji.exception.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class ApiController {

    @GetMapping("")
    public User get(@RequestParam(required = false) String name, @RequestParam(required = false) Integer age){
        User user = new User();
        user.setName(name);
        user.setAge(age);

        int a = 10 + age;

        return user;
    }

    @PostMapping("")
    public User post(@Valid @RequestBody User user){

        System.out.println(user);

        return user;
    }



    //이런 식으로 해당 API 내부에서도 각각처리 가능
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e){
        System.out.println("API controller 내부");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}

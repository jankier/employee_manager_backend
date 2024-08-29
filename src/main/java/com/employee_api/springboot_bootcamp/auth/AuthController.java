package com.employee_api.springboot_bootcamp.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> loginEmployee(@RequestBody AuthRequest authRequest) {
        authService.login(authRequest);
        log.info("Employee logged in with");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

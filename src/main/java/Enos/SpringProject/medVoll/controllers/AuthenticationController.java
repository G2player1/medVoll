package Enos.SpringProject.medVoll.controllers;

import Enos.SpringProject.medVoll.domain.models.User;
import Enos.SpringProject.medVoll.domain.models.dto.reads.ReadTokenDTO;
import Enos.SpringProject.medVoll.domain.models.dto.registers.RegisterUserDTO;
import Enos.SpringProject.medVoll.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity loginUser(@RequestBody @Valid RegisterUserDTO registerUserDTO){
        var token = new UsernamePasswordAuthenticationToken(registerUserDTO.login(),registerUserDTO.senha());
        var authentication = authenticationManager.authenticate(token);
        var tokenDTO = new ReadTokenDTO(tokenService.generateToken((User) authentication.getPrincipal()));
        return ResponseEntity.ok(tokenDTO);
    }
}

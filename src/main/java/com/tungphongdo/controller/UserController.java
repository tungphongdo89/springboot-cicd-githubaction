package com.tungphongdo.controller;

import com.tungphongdo.entity.User;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.custom-client-id}")
    private String customClientId;

    private final Keycloak keycloak;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User userRequest) {
        // Create the user representation
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userRequest.getUsername());
        user.setEnabled(true);
        user.setEmail(userRequest.getEmail());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userRequest.getPassword());
        credential.setTemporary(Boolean.FALSE);

        user.setCredentials(Collections.singletonList(credential));

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        Response result = usersResource.create(user);
        if (result.getStatus() == 201) {
            // Get the newly created user ID
            String userId = result.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

            // Assign realm roles to the user
            RoleResource roleResource = realmResource.roles().get(userRequest.getRole());
            RoleRepresentation userRole = roleResource.toRepresentation();
            UserResource userRealmResource = realmResource.users().get(userId);
            userRealmResource.roles().realmLevel().add(List.of(userRole));

            // Assign custom client roles to user
//            ClientResource clientResource = realmResource.clients().get(customClientId);
            ClientRepresentation clientRepresentation = realmResource.clients().findByClientId("dev-demo-github-action-cicd-credentials").get(0);
            ClientResource clientResource = realmResource.clients().get(clientRepresentation.getId());
            RoleResource clientRoleResource = clientResource.roles().get("USER");
            RoleRepresentation clientRoleRepresentation = clientRoleResource.toRepresentation();
            userRealmResource.roles().clientLevel(clientRepresentation.getId()).add(List.of(clientRoleRepresentation));

            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully with role: " + userRequest.getRole());
        } else {
            return ResponseEntity.status(result.getStatus()).body("User creation failed");
        }


    }


}

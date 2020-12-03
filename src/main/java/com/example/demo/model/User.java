package com.example.demo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstname;
    private String lastname;
    private String address;
    private String email;
    private String password;
    private String role;

    // < For Spring Security >
    private int active;
    private String roles = "";
    private String permissions = "";
    private String username = "";

    public User(String username, String password, String roles, String permissions){
        this.username=username;
        this.password=password;
        this.roles=roles;
        this.permissions=permissions;
        this.active = 1;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { username = username; }
    public int getActive() { return active; }
    public void setActive(int active) { this.active = active; }
    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }
    public String getPermissions() { return permissions; }
    public void setPermissions(String permissions) { this.permissions = permissions; }

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
    public List<String>getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
    // </ For Spring Security>

//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
//    private Set<CreditProfile> creditProfiles;

    public User(){}

//    public User(String firstname, String lastname, String address, String email, String password,String role){
//
//        this.firstname=firstname;
//        this.lastname=lastname;
//        this.address=address;
//        this.email=email;
//        this.password=password;
//        this.role=role;
//    }

//    @Override
//    public String toString(){
//        return String.format("User[id=%d, firstname='%s', lastname='%s', address='%s', email='%s',password='%s']",
//                id,firstname,lastname,address,email,password);
//    }

    public int getId(){
        return id;
    }

    public String getFirstName(){
        return firstname;
    }

    public String getLastName(){
        return lastname;
    }

    public String getAddress(){
        return address;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){return password;}

    public String getRole(){return role;}

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role){
        this.role=role;
    }
}

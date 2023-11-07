package com.apiproyect.NotUberServer.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "notuberdb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    @Column
    private String companyID;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String location;
    @Column
    private String role;

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getCompanyID(){
        return companyID;
    }
    public void setCompanyID(String companyID){
        this.companyID = companyID;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }
}

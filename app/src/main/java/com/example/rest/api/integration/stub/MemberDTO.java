package com.example.rest.api.integration.stub;

import org.json.JSONException;
import org.json.JSONObject;


public class MemberDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JSONObject getJsonObject() {
        JSONObject dto = new JSONObject();
        try {
            dto.put("firstName", this.firstName);
            dto.put("lastName", this.lastName);
            dto.put("email", this.email);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  dto;
    }
}

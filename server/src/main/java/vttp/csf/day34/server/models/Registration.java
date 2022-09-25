package vttp.csf.day34.server.models;

import java.io.Serializable;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

public class Registration implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;//optional
    private String name;
    private String email;
    private String mobile;

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public static Registration create(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject data = reader.readObject();

        final Registration reg = new Registration();
        reg.setName(data.getString("name"));
        reg.setEmail(data.getString("email"));
        reg.setMobile(data.getString("mobile"));

        // since id is optional,check if id is in the payload
        if (data.containsKey("id"))
            reg.setId(data.getString("id"));

        return reg;

    }

    public static String getId(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject data = reader.readObject();

      String id = data.getString("id");
        return id;
    }

    public JsonObject toJson(){
        return Json.createObjectBuilder()
            .add("id",id)
            .add("name",name)
            .add("email",email)
            .add("mobile",mobile)
            .build();
    }
    
}

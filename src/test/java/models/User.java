package models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String email;
    @SerializedName("avatar")
    private String avatarLink;
}

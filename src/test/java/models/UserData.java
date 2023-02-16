package models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserData {
    @SerializedName("data")
    private User user;
}


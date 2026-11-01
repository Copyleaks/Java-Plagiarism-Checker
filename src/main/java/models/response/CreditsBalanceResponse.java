package models.response;

import com.google.gson.annotations.SerializedName;

public class CreditsBalanceResponse {
    @SerializedName("Amount")
    private int amount;

    public int getAmount() {
        return amount;
    }
}

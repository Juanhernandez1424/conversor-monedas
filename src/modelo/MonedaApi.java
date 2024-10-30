package modelo;

import com.google.gson.annotations.SerializedName;

public record MonedaApi(
        @SerializedName("base_code") String monedaBase,
        @SerializedName("target_code") String monedaCambio,
        @SerializedName("conversion_rate") double tasaCambio) {
}

package modelo;

import com.google.gson.annotations.SerializedName;

public class Moneda {
  private String monedaBase;
  private String monedaCambio;
  private double tasaCambio;

  public Moneda(MonedaApi monedaApi) {
    this.monedaBase = monedaApi.monedaBase();
    this.monedaCambio = monedaApi.monedaCambio();
    this.tasaCambio = monedaApi.tasaCambio();
  }

  public String getMonedaBase() {
    return monedaBase;
  }

  public String getMonedaCambio() {
    return monedaCambio;
  }

  public double getTasaCambio() {
    return tasaCambio;
  }
}

package solicitudes;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Moneda;
import modelo.MonedaApi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.Scanner;

public class ConsumoApi {
  String[] monedas = {"ARS", "BOB", "BRL", "CLP", "COP", "USD"};
  String apiKey = "8e73835d505db62ae392ece5";
  String url = "https://v6.exchangerate-api.com/v6/";

  public void api() throws IOException, InterruptedException {
    Scanner sc = new Scanner(System.in);

    int option = 1;

    while(option != 0){
      if(option==1){
        System.out.println("CONVERSOR DE MONEDAS");
        String monedaBase = seleccionaMoneda(sc, "base");
        String monedaCambio = seleccionaMoneda(sc, "cambio");

        System.out.println("Ingrese el valor que desea cambiar");
        String valor = sc.next();

        realizarCambioMoneda(monedaBase, monedaCambio, valor);

        System.out.println("Ingrese 0 para salir 1 para continuar");
        option = sc.nextInt();
        if(option > 1 || option < 0){
          System.out.println("Ingrese un valor válido");
          System.out.println("Ingrese 0 para salir 1 para continuar");
          option = sc.nextInt();
        }
      } else if(option == 0){
        break;
      }
    }
  }

  private String seleccionaMoneda(Scanner sc, String tipoMoneda){
    System.out.println("Seleccionar la moneda " + tipoMoneda + ":");
    for (int i = 0; i < monedas.length; i++) {
      System.out.println((i+1) + ". " + monedas[i]);
    }

    int optionMoneda = sc.nextInt();

    if(optionMoneda < 1 || optionMoneda > monedas.length){
      System.out.println("Seleccione una de las monedas disponibles");
      return seleccionaMoneda(sc, tipoMoneda);
    }

    return monedas [optionMoneda - 1];
  }

  public void realizarCambioMoneda(String monedaBase, String monedaCambio, String valor){
    String urlApi = url + apiKey + "/pair/" + monedaBase + "/" + monedaCambio + "/" + valor;
    System.out.println(urlApi);

    try {
      // Petición API
      HttpClient client = HttpClient.newHttpClient();

      // Request API
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(urlApi))
              .build();
      HttpResponse<String> response = client
              .send(request, HttpResponse.BodyHandlers.ofString());

      if(response.statusCode() == 200){
        // Body respuesta JSON
        String json = response.body();
        System.out.println(json);

        //Convertir JSON a estructura JAVA
        Gson gson =new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        MonedaApi monedaApi = gson.fromJson(json, MonedaApi.class);

        Moneda moneda = new Moneda(monedaApi);

        //Cálculo resultado de cambio
        double resultadoCambio = Double.valueOf(valor) * moneda.getTasaCambio();
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

        // Impresión de datos y resultados al cliente
        System.out.println("Resultado: " +
                decimalFormat.format(Double.valueOf(valor)) + " " + monedaBase + " en " + monedaCambio +
                " son= " + decimalFormat.format(resultadoCambio) + " " + monedaCambio + ", con tasa de cambio= " + moneda.getTasaCambio() + " al día de hoy");
      } else{
        System.out.println("Error respuesta de API: " + response.statusCode());
      }
    } catch (IOException | InterruptedException e){
      System.out.println("Error de conexión con api: " +e.getMessage());
    } catch (NumberFormatException e){
      System.out.println("Error en formato numérico: " + e.getMessage());
    } finally {
      System.out.println("Ejecutado!!!");
    }
  }
}

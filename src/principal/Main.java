package principal;

import solicitudes.ConsumoApi;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException {
    ConsumoApi consumoApi = new ConsumoApi();
    consumoApi.api();
  }
}
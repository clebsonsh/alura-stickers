import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws Exception {

        // Pega a API_KEY do arquivo env.properties
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream("env.properties")) {

            // load a properties file
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String API_KEY = prop.getProperty("API_KEY");

        // Fazer um conexão HTTP e buscar os filmes mais populares
        // Gerar uma URI como o path da API + a API_KEY

        // Api do imdb esta forá do ar
        // String url = "https://imdb-api.com/en/API/MostPopularMovies/";
        // URI endereco = URI.create(url + API_KEY);

        // Usando um API alternativa por hora
        String url = "https://alura-filmes.herokuapp.com/conteudos";
        URI endereco = URI.create(url);

        // Faz o GET dos dados da API
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request,
                BodyHandlers.ofString());
        String body = response.body();

        // Monstra os dados retornados da API no console
        // System.out.println(body);

        // Extrair só os dados que interessam (titulo, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // Exibir e manipular os dados
        for (Map<String, String> filme : listaDeFilmes) {
            // Alguns filmes não tem nota, então estou colocando 0 para os mesmos
            String rating = filme.get("imDbRating") != ""
                    ? filme.get("imDbRating")
                    : "0";

            System.out.println("Título: \033[1m" + filme.get("title") + "\033[0m");
            System.out.println("Poster: \033[1m" + filme.get("image") + "\033[0m");
            System.out.println("\u001B[45mClasificação: " + rating + "\u001B[0m");

            for (int i = 0; i < Math.floor(Float.parseFloat(rating)); i++) {
                System.out.print("\u2B50");
            }

            System.out.println();
            System.out.println();
        }

    }
}

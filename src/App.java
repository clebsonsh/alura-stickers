import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
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
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java/api/TopMovies.json";
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
        var geradora = new GeradoraDeFigurinhas();
        for (Map<String, String> filme : listaDeFilmes) {
            String titulo = filme.get("title");

            String urlImagem = filme.get("image");
            String nomeArquivo = titulo + ".png";

            InputStream input = new URL(urlImagem).openStream();

            geradora.cria(input, nomeArquivo);

            System.out.println("Título: \033[1m" + titulo + "\033[0m");
        }

    }
}

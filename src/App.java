import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
//         Fazer um conexão HTTP e buscar os filmes mais populares

//         Api do imdb esta forá do ar
//        ApiKey apiKey = new ApiKey();
//        String API_KEY = apiKey.getApiKey();
//         String url = "https://imdb-api.com/en/API/MostPopularMovies/" + API_KEY;

//         Usando um API imdb alternativa por hora
//        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java/api/TopMovies.json";
//        ExtratorDeConteudo extrator = new ExtratorDeConteudoDoImdb();

//         Usando API da NASA
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java/api/NASA-APOD-JamesWebbSpaceTelescope.json";
        ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();

//         Faz o GET dos dados da API

        ClienteHttp clienteHttp = new ClienteHttp();
        String json = clienteHttp.buscaDados(url);

        List<Conteudo> conteudos =  extrator.extraiConteudos(json);

//         Exibir e manipular os dados
        var geradora = new GeradoraDeFigurinhas();

        for (Conteudo conteudo : conteudos) {

            String nomeArquivo = conteudo.getTitulo() + ".png";
            InputStream input = new URL(conteudo.getUrlImagem()).openStream();

            geradora.cria(input, nomeArquivo);

            System.out.println("Título: \033[1m" + conteudo.getTitulo() + "\033[0m");
        }

    }
}

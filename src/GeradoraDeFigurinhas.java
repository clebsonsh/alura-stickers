import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {

  public void cria(InputStream input, String nomeArquivo) throws Exception {

    // String imgURL = "https://imersao-java-apis.s3.amazonaws.com/TopMovies_9.jpg";
    // InputStream input = new URL(imgURL).openStream();

    // Leitura da imagem
    BufferedImage imagemOriginal = ImageIO.read(input);

    // Criar nova imagem em memória com transparência e com tamanho novo
    int largura = imagemOriginal.getWidth();
    int altura = imagemOriginal.getHeight();
    int novaAltura = altura + 200;

    BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

    // Copiar a imagem original para nova imagem (em memória)
    Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
    graphics.drawImage(imagemOriginal, 0, 0, null);

    // Configurar font
    Font fonte = new Font(Font.SANS_SERIF, Font.BOLD, 82);
    graphics.setFont(fonte);

    graphics.setColor(Color.YELLOW);

    // Escrever uma frase na nova imagem
    graphics.drawString("TOPZERA", (largura / 2) - 220, altura + 125);

    // Escrever a nova imagem em arquivo

    File folder = new File("saida");

    if (!folder.exists()) {
      folder.mkdir();
    }

    ImageIO.write(novaImagem, "png", new File("saida/" + nomeArquivo));

  }
}

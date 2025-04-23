package br.com.misatech.gerador.view.uc;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Classe responsável por renderizar um arquivo de imagem em um objeto do tipo
 * Component.
 * 
 * @author Misael da Costa Homem
 *
 */
public class UCImageLoader extends Component {

	private static final long serialVersionUID = -4354464211217833890L;
	private BufferedImage bufferedImage;

	/*
	 * Construtor padrão.
	 */
	public UCImageLoader() {
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(bufferedImage, 0, 0, null);
	}

	/**
	 * Carregar uma imagem.
	 * 
	 * @param file Um objeto que representa um arquivo.
	 * @return Retorna um tipo Component com a imagem carregada.
	 * @throws Exception Caso algum erro no carregamento ocorra, dispara uma
	 *                   exceção.
	 */
	public Component loadImage(Object file) throws Exception {

		/*
		 * Como esta classe extende a classe Component, para fazer com que o método
		 * print seja executado, atribui a variável local "c" a esta mesma classe.
		 */
		Component c = this;

		if (file != null) {

			// Verifica se a instância é do tipo File.
			if (file instanceof File) {
				bufferedImage = ImageIO.read((File) file);
				// bufferedImage = bufferedImage.getScaledInstance(width, height, hints)
			}

			// Verifica se a instância é do tipo InputStream.
			if (file instanceof InputStream) {
				bufferedImage = ImageIO.read((InputStream) file);
			}

			c.setPreferredSize(new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()));

		}

		return c;

	}

}

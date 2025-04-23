package br.com.misatech.gerador.model;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class FormUtils {

	private static Dimension dimensaoMonitor = Toolkit.getDefaultToolkit().getScreenSize();
	private static boolean existeCaracterIndevido = false;

	/**
	 * Define os tipos de m�scaras usadas nos campos JFormattedText.
	 * 
	 * @param pCampo
	 * @param pTipo
	 * @return MaskFormatter
	 */
	public static MaskFormatter formatarMascaraCampos(JFormattedTextField pCampo, MascaraCampo pTipo) {

		MaskFormatter maskFormatter = new MaskFormatter();

		try {

			switch (pTipo) {
			case enData:
				maskFormatter.setMask(MascaraCampo.enData.getValor());
				maskFormatter.setPlaceholderCharacter('0');
				break;

			case enHora:
				maskFormatter.setMask(MascaraCampo.enHora.getValor());
				maskFormatter.setPlaceholderCharacter('0');
				break;

			case enDataHora:
				maskFormatter.setMask(MascaraCampo.enDataHora.getValor());
				maskFormatter.setPlaceholderCharacter('0');
				break;

			case enCPF:
				maskFormatter.setMask(MascaraCampo.enCPF.getValor());
				maskFormatter.setPlaceholderCharacter('0');
				break;

			case enCNPJ:
				maskFormatter.setMask(MascaraCampo.enCNPJ.getValor());
				maskFormatter.setPlaceholderCharacter('0');
				break;

			case enRG:
				maskFormatter.setMask(MascaraCampo.enRG.getValor());
				maskFormatter.setPlaceholderCharacter('0');
				break;

			default:
				maskFormatter.setMask("");
				break;

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return maskFormatter;
	}

	/**
	 * M�todo para maximizar o tamanho do formul�rio para o tamanho da tela do
	 * monitor.
	 * 
	 * @param pForm
	 */
	public static void setMaximizarForm(JFrame pForm) {

		pForm.setSize(dimensaoMonitor.width, dimensaoMonitor.height);

	}

	public static void setCentralizarForm(JFrame pForm) {

		pForm.setLocation((dimensaoMonitor.width - pForm.getWidth()) / 2,
				(dimensaoMonitor.height - pForm.getHeight()) / 2);

	}

	public static void setCentralizarForm(JDialog pForm) {

		pForm.setLocation((dimensaoMonitor.width - pForm.getWidth()) / 2,
				(dimensaoMonitor.height - pForm.getHeight()) / 2);

	}

	public static void setCentralizarForm(JInternalFrame pInterForm) {

		pInterForm.setLocation((dimensaoMonitor.width - pInterForm.getWidth()) / 2,
				(dimensaoMonitor.height - pInterForm.getHeight()) / 2);

	}

	/**
	 * M�todo para prevenir o uso de caractere indevido nos campos dos formul�rios.
	 * 
	 * @param recipiente - o controle que contem outros controles.
	 * @return Retorna true se encontrado um caractere indevido, que n�o deve se
	 *         utilizado.
	 */
	public static boolean verificarCaracterIndevido(Container recipiente) {

		/*
		 * Guardar em array de componentes, todos os componentes. do componente
		 * recipiente.
		 */
		Component componentes[] = recipiente.getComponents();

		// Vair�vel de marca��o (flag).
		// boolean existeCaracterIndevido = false;

		// Itera��o for in, para cada coponente nos componentes...
		for (Component componente : componentes) {

			// Verifica:
			// O controle � do tipo JTextField?
			if (componente instanceof JTextField) {

				// Atruibuir o valor do campo de texto a vari�vel.
				String texto = ((JTextField) componente).getText();

				// O texto contem pipe ou ap�strofe?
				if (texto.contains((CharSequence) "|") || texto.contains((CharSequence) "'")) {

					// Configura a flag para true.
					existeCaracterIndevido = true;
					// Sai do la�o for in;
					break;

					// Se n�o...
				} else {

					// Mant�m a vari�vel(atributo de classe) como false, caso n�o encontre nada.
					existeCaracterIndevido = false;

				}

			}

			// Chamada recursiva passando o componente como recipiente (Container).
			verificarCaracterIndevido((Container) componente);

		}

		return existeCaracterIndevido;

	}

	/**
	 * Verifica em uma sequ�ncia de string a exist�ncia de caracteres que n�o devem
	 * ser usados.
	 * 
	 * @return Retorna true se encontrado um caractere indevido, que n�o deve se
	 *         utilizado.
	 */
	public static boolean verificarCaracterIndevido(String texto) {

		boolean existeCaracterIndevido = false;

		for (int i = 0; i < texto.length(); i++) {

			/*
			 * Os seguintes caracteres:
			 * 
			 * \ / : * ? " < > |
			 * 
			 * foram encontrados no texto?
			 */
			if (texto.charAt(i) == '\\' || texto.charAt(i) == '/' || texto.charAt(i) == ':' || texto.charAt(i) == '*'
					|| texto.charAt(i) == '?' || texto.charAt(i) == '\"' || texto.charAt(i) == '<'
					|| texto.charAt(i) == '>' || texto.charAt(i) == '|') {

				existeCaracterIndevido = true;

			}

		}

		return existeCaracterIndevido;

	}

	/**
	 * Incializa todos os campos do formul�rio e de grupo de controles.
	 * 
	 * @param recipiente - o controle que possui(recipiente) outros controles.
	 */
	public static void inicializarCampos(Container recipiente) {

		Component componentes[] = recipiente.getComponents();

		for (Component componente : componentes) {

			if (componente instanceof JTextField) {

				((JTextField) componente).setText("");

			}

			if (componente instanceof JFormattedTextField) {

				((JFormattedTextField) componente).setText("");

			}

			// Chamada recursiva passando o componente como recipiente (Container).
			inicializarCampos((Container) componente);

		}

	}

	/**
	 * M�todo para formatar as mensagens de rota de pilha em um JOptionPane<br>
	 * http://www.guj.com.br/java/244038-resolvido-printstacktrace-em-joptionpane
	 * 
	 * @param thr - o objeto da exce��o lan�ada.
	 * @return Retorna uma string contendo o valor formatado para ser exibido na
	 *         GUI.
	 */
	public static String formataMensagemExcecao(Throwable thr) {

		/*
		 * Exemplo de uso:
		 * 
		 * try { int x = 100 / 0; } catch (Exception ex) { JOptionPane.showMessageDialog
		 * (null, formataMensagemExcecao (ex), "Erro", JOptionPane.ERROR_MESSAGE); }
		 */

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		thr.printStackTrace(printWriter);

		return stringWriter.toString();

	}

}

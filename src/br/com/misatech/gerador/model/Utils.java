package br.com.misatech.gerador.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Classe utilitária para formatação de datas, hora, valores, etc.
 * 
 * @author Misael C. Homem
 * 
 */
public class Utils {

	/**
	 * Formatar a data e hora no padrão brasileiro .
	 */
	public String getDataHoraAtual() {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        GregorianCalendar calendar = new GregorianCalendar();
        Date date = calendar.getTime(); 
        String data = dateFormat.format(date);
        
		return data;
		
	}
	
	/**
	 * Obtém a data e hora atuais da estação local.
	 * @return Retorna a data e hora no formato - dd/MM/yyyy HH:mm:ss.
	 */
	public String getDataHora() {
		
		Locale localizacao = new Locale("pt", "BR"); 
		GregorianCalendar calendar = new GregorianCalendar(); 
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", localizacao); 
		
		return formatador.format(calendar.getTime());
		
	}
}

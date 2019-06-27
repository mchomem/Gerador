package br.com.misatech.gerador.model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import br.com.misatech.gerador.model.bo.BoArquivoConfiguracao;
import br.com.misatech.gerador.model.vo.VoArquivoConfiguracao;

/**
 * Classe para operar um "CRUD" de arquivo, para o arquivo de configuração.
 * @author Misael
 *
 */
public class DaoArquivoConfiguracao {
	
	// TODO segregar as operações minuciosas e/ou repetitivas desta classe e colocá-las numa classe do tipo DAO genérica para arquivo.
	
	private String       nomeArquivo;
	private final String caminhoArquivo = "config/";
	
	public DaoArquivoConfiguracao() {}
	
	public void gravar(VoArquivoConfiguracao voArquivoConfiguracao) throws FileNotFoundException, IOException {
		
		// Informar o nome e caminho do arquivo.
		nomeArquivo    =  voArquivoConfiguracao.getNome() + ".properties";
		
		File dir = new File(caminhoArquivo);
		
		if(!dir.exists()) {
			dir.mkdir();
		}
		
		File file = new File(caminhoArquivo + nomeArquivo);
		
		if(!file.exists()) {
			file.createNewFile();
		}
		
		// Instanciar a classe Properties para preparar um arquivo deste tipo.
		Properties properties = new Properties();
		
		// Criar o fluxo de escrita (exige que o arquivo estaja criado antes no disco).
		FileInputStream fis = new FileInputStream(file);
		
		// Carrega o arquivo para escrever nele com a classe Properties.
		properties.load(fis);
		
		// Escreve no arquivo com método put da classe Properties.
		properties.put("usuario",   voArquivoConfiguracao.getUsuario());
		properties.put("sgbdr",     voArquivoConfiguracao.getSgbdr());
		properties.put("url",       voArquivoConfiguracao.getUrl());
		properties.put("driver",    voArquivoConfiguracao.getDriver());
		properties.put("senha",     new BoArquivoConfiguracao(voArquivoConfiguracao).senhaString());
		properties.put("descricao", voArquivoConfiguracao.getDescricao());
		properties.put("data_hora", voArquivoConfiguracao.getDataHora());
		
		FileOutputStream fos = new FileOutputStream(file);
		
		// Escreve no arquivo .properties.
		properties.store(fos , " Configuração Servidor para aplicação.");
		// Encerra fluxo de escrita.
		
		fos.flush();
		fos.close();
		
		fos        = null;
		properties = null;
		fis        = null;
		
	}
	
	public void excluir(VoArquivoConfiguracao voArquivoConfiguracao) throws IOException {
		
		nomeArquivo = voArquivoConfiguracao.getNome() + ".properties";
		File arquivo = new File(caminhoArquivo + nomeArquivo);
		arquivo.delete();
		arquivo = null;
		
	}
	
	public VoArquivoConfiguracao consultar(String nome) throws IOException {
		
		nomeArquivo = nome + ".properties";
		File arquivo = new File(caminhoArquivo + nomeArquivo);
		
		if(!arquivo.exists()) {
			return null;
		}
		
		VoArquivoConfiguracao voArquivoConfiguracao = new VoArquivoConfiguracao();
		Properties properties = new Properties();
		FileInputStream fis   = new FileInputStream(caminhoArquivo + nomeArquivo);
		properties.load(fis);
		
		voArquivoConfiguracao.setNome(nome);
		voArquivoConfiguracao.setSgbdr(properties.getProperty("sgbdr"));
		voArquivoConfiguracao.setUsuario(properties.getProperty("usuario"));
		voArquivoConfiguracao.setUrl(properties.getProperty("url"));
		voArquivoConfiguracao.setDriver(properties.getProperty("driver"));
		voArquivoConfiguracao.setSenha(new BoArquivoConfiguracao(voArquivoConfiguracao).senhaChar(properties.getProperty("senha")));
		voArquivoConfiguracao.setDescricao(properties.getProperty("descricao"));
		voArquivoConfiguracao.setDataHora(properties.getProperty("data_hora"));
		
		properties = null;
		fis.close();
		fis        = null;
		arquivo    = null;
		
		return voArquivoConfiguracao;
		
	}
	
	public List<VoArquivoConfiguracao> consultar() throws IOException {
		
		/* Preparar um filtro para obter somente arquivos
		 * com extensão ".properties" para evitar a recuperação
		 * de nomes de arquivos com outras extensões. */
		FilenameFilter filtro = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				
				return name.endsWith(".properties");
				
			}
			
		};
		
		File diretorio = new File(caminhoArquivo);
		String[] nomesArquivos = diretorio.list(filtro);
		List<VoArquivoConfiguracao> listaArquivosConfiguracao = new ArrayList<VoArquivoConfiguracao>();
		
		for(int i = 0; i < nomesArquivos.length; i++) {
			
			VoArquivoConfiguracao voArquivoConfiguracao = new VoArquivoConfiguracao();
			Properties properties = new Properties();
			FileInputStream fis   = new FileInputStream(caminhoArquivo + nomesArquivos[i]);
			properties.load(fis);
			
			String nomeArquivoSemExtencao = nomesArquivos[i].substring(0, nomesArquivos[i].length() - 11);
			
			voArquivoConfiguracao.setNome(nomeArquivoSemExtencao);
			voArquivoConfiguracao.setSgbdr(properties.getProperty("sgbdr"));
			voArquivoConfiguracao.setUsuario(properties.getProperty("usuario"));
			voArquivoConfiguracao.setUrl(properties.getProperty("url"));
			voArquivoConfiguracao.setDriver(properties.getProperty("driver"));
			voArquivoConfiguracao.setSenha(new BoArquivoConfiguracao(voArquivoConfiguracao).senhaChar(properties.getProperty("senha")));
			voArquivoConfiguracao.setDescricao(properties.getProperty("descricao"));
			voArquivoConfiguracao.setDataHora(properties.getProperty("data_hora"));
			
			listaArquivosConfiguracao.add(voArquivoConfiguracao);
			
		}
		
		return listaArquivosConfiguracao;
		
	}

}
















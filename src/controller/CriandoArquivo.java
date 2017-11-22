package controller;
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;
public class CriandoArquivo {
	private static final String path = "aluno.txt";
		public static void main(String[] args) throws IOException {
			File file = new File(path);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("Dados gravados corretamente");
			//writer.newLine();
			//Criando o conteúdo do arquivo
			writer.flush();
			//Fechando conexão e escrita do arquivo.
			writer.close();
	}
}

package main.DAO;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileManager {
	private FileReader fileReader;

	public FileManager() {
		try {
			fileReader = new FileReader("data/config.txt");
		} catch (FileNotFoundException e) {
			System.out.println("config파일이 존재하지 않습니다.");
			e.printStackTrace();
		}
	}

	public String readLine() {
		char[] content = {};
		int temp_int = -1;
		while (true) {
			int content_size = content.length;

			try {
				temp_int = fileReader.read();

			} catch (IOException e) {
				System.out.println("config파일을 읽어들일 수 없습니다.");
				e.printStackTrace();
			}

			if (temp_int == -1 || temp_int == 10)
				break;
			if (temp_int != 13) {
				char temp_char = (char) temp_int;
				char[] temp_content = new char[content_size + 1];
				for (int i = 0; i < content_size; i++) {
					temp_content[i] = content[i];
				}
				temp_content[content_size] = temp_char;
				content = temp_content;
			}

		}

		String line = new String();
		for (int i = 0; i < content.length; i++) {
			line = line + content[i];
		}
		return line;
	}

	public String[] readAllLines() {
		String[] lines = {};
		while (true) {
			String line = readLine();
			if (line.length() == 0)
				break;
			int lines_size = lines.length;
			String[] temp_line = new String[lines_size + 1];
			for (int i = 0; i < lines_size; i++) {
				temp_line[i] = lines[i];
			}
			temp_line[lines_size] = line;
			lines = temp_line;
		}
		return lines;
	}
}

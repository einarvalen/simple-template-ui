package org.fyfa.samples.dbcrud;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Duns {

	public static void main(String[] args) throws Exception {
		//measureFieldSize();
		changeSeparator();
	}

	private static void measureFieldSize() throws Exception {
		String fileSource = "/media/einar/Warehouse/Watts/7101_SalesInvoice.csv";
		BufferedReader reader = new BufferedReader(new FileReader(fileSource));
		String line = null;
		int lineCount = 0, fieldMinCount = 100000, fieldMaxLen = 0;
		while ((line = reader.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, "\t", true);
			int i = 0;
			for (; st.hasMoreTokens(); ++i) {
				String token = st.nextToken();
				int len = token.length();
				if (len > fieldMaxLen) {
					fieldMaxLen = len;
					//System.out.println(String.format("LineCount:%s, Fieldmax:%s\n", lineCount, fieldMaxLen));
				}
			}
			++lineCount;
			if (i < fieldMinCount) {
				fieldMinCount = i;
				System.out.println(String.format("LineCount:%s, FieldMinCount:%s\n", lineCount, fieldMinCount));
			}
		}
		reader.close();
		System.out.println(String.format("Done. LineCount:%s, Fieldmax:%s, FieldMinCount:%s\n", lineCount, fieldMaxLen, fieldMinCount));
	}

	private static void changeSeparator() throws Exception {
		String fileSource = "/media/einar/Warehouse/Watts/7101_SalesInvoice.csv";
		String fileSourceWrite = "/media/einar/Warehouse/Watts/7101_SalesInvoice2.csv";
		BufferedReader reader = new BufferedReader(new FileReader(fileSource));
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileSourceWrite));
		String line = null;
		int lineCount = 0, fieldCount = 0;
		while ((line = reader.readLine()) != null) {
			++lineCount;
			StringTokenizer st = new StringTokenizer(line, "\t", true);
			StringBuilder sb = new StringBuilder();
			int i = 0;
			for (; st.hasMoreTokens();) {
				String token = st.nextToken("\t");
				if (token.equals("\t")) {
					sb.append(";");
					++i;
				} else {
					sb.append(token.trim());
				}
			}
			int j = i;// / 2;
			for (; j < 18; j++) {
				//sb.append(";");
			}
			if (j > fieldCount) fieldCount = j;
			System.out.println(String.format("Done. LineCount:%s, FieldCount:%s\n", lineCount, j));
			sb.append(";\r\n");
			writer.append(sb.toString());
		}
		System.out.println(String.format("Done. LineCount:%s, FieldCount:%s\n", lineCount, fieldCount));
		reader.close();
		writer.close();
	}

}

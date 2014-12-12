package org.fyfa.samples.dbcrud;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class Duns {

	public static void main(String[] args) throws Exception {
		String fileSource = "/media/einar/Warehouse/Watts/WattsFileFINAL.csv";
		String fileSourceWrite = "/media/einar/Warehouse/Watts/DAB_Customers.txt";
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
					sb.append("|");
				} else {
					sb.append(token.trim());
				}
				++i;
			}
			int j=i/2;
			for (;j <154; j++) {
				sb.append("|");
			}
			if (j > fieldCount) fieldCount = j;
			System.out.println(String.format("Done. LineCount:%s, FieldCount:%s\n", lineCount, j));
			sb.append("|\r\n");
			writer.append(sb.toString());
		}
		System.out.println(String.format("Done. LineCount:%s, FieldCount:%s\n", lineCount, fieldCount));
		reader.close();
		writer.close();
	}
	
}

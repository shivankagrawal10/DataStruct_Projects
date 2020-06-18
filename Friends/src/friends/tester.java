package friends;

import java.io.*;
import java.util.*;
import java.util.Scanner;



public class tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String file = "t.txt";
		//String file = "3.txt";
		String file = "subtest5.txt";
		try {
			Scanner sc = new Scanner(new File(file));
			Graph first = new Graph(sc);
			sc.close();
			first.GraphPrint();
			Friends.shortestChain(first, "p301", "p198");
			Friends.cliques(first,"rutgers");
			Friends.connectors(first);
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

}

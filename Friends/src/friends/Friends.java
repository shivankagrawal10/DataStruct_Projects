package friends;


import java.util.ArrayList;



import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		ArrayList<String> shortest = new ArrayList<String>();
		ArrayList<Person> visited = new ArrayList<Person>();
		ArrayList<Person> pred = new ArrayList<Person>();
		Person start = g.members[g.map.get(p1)];
		if(start.first==null) {return null;}
		Person curr = start;
		int counter=0;
		do {
			
			Friend f = curr.first;
			while(f!=null && visited.contains(g.members[g.map.get(p2)])==false) {
				if (visited.contains(g.members[f.fnum])==false) {
					pred.add(curr);
					visited.add(g.members[f.fnum]);
					System.out.println(g.members[f.fnum].name);
				}
				f = f.next;
			}
			
			curr = visited.get(counter);
			counter++;
		} while(visited.contains(g.members[g.map.get(p2)])==false || counter<visited.size());
		if (visited.contains(g.members[g.map.get(p2)])==false){
			return null;
		}
		Person end = g.members[g.map.get(p2)];
		Stack <String> names = new Stack<String>();
		//names.push(end.name);
		while(end!=start) {
			names.push(end.name);
			end = pred.get(visited.indexOf(end));
		}
		names.push(end.name);
		while(names.isEmpty()==false) {
			shortest.add(names.pop());
		}
		System.out.println("Shortest path: ");
		System.out.println(shortest);
		return shortest;
	}
	
	/*		Heap Methods
	private static void heapupdate(ArrayList<Person>heap,ArrayList<Integer>weights, ArrayList<Person>weightnames,int weight) {
		if(heap.isEmpty()||heap.size()<2) {
			return;
		}
		Person curr = heap.get(heap.size()-1);
		int ind = heap.size()-1;
		while(ind!=0 && weight<weights.get(weightnames.indexOf((heap.get((ind-1)/2))))) {
			//Person temp = curr;
			heap.set(ind,heap.get((ind-1)/2));
			heap.set((ind-1)/2,curr);
			ind = (ind-1)/2;
			//curr = heap.get(ind);
		}
	}
	private static void siftdown(ArrayList<Person>heap,ArrayList<Integer>weights,ArrayList<Person>weightnames, int weight) {
		if(heap.isEmpty()||heap.size()<2) {
			return;
		}
		Person curr = heap.get(0);
		int ind = 0;
		while(2*ind+1<heap.size()) {
			if(weight>weights.get(weightnames.indexOf(heap.get(2*ind+1)))||2*ind+1==heap.size()-1 ) {
				if(2*ind+1==heap.size()-1 ||weights.get(weightnames.indexOf(heap.get(2*ind+1)))<weights.get(weightnames.indexOf(heap.get(2*ind+2)))) {
					heap.set(ind,heap.get(2*ind+1));
					heap.set(2*ind+1,curr);
					ind = 2*ind+1;
				}
				else {
					heap.set(ind,heap.get(2*ind+2));
					heap.set(2*ind+2,curr);
					ind = 2*ind+2;
				}
			}
			else if(weight>weights.get(weightnames.indexOf(heap.get(2*ind+2)))) {
				
					heap.set(ind,heap.get(2*ind+2));
					heap.set(2*ind+2,curr);
					ind = 2*ind+2;
				
			}
			else {
				break;
			}
			
			
		}
	}
	*/
	
	/*			Djikstra's method
	 * ArrayList <Person> visited = new ArrayList<Person>();
		//ArrayList <Person>unvisited = new ArrayList<Person>();
		ArrayList <Person>weightnames = new ArrayList<Person>();
		ArrayList <Integer> weights = new ArrayList <Integer> ();
		ArrayList <Person> heapfringe = new ArrayList<Person>();
		Person start = g.members[g.map.get(p1)];
		weightnames.add(start);
		weights.add(0);
		int prev = 0;
		Person pivot = start;
		boolean further = false;
		if(pivot.first==null) {
			return null;
		}
		do {
			further = false;
			Friend fringe = pivot.first;
			visited.add(pivot);
			while(fringe!=null) {
				//System.out.print(g.members[fringe.fnum].name);
				if(visited.contains(g.members[fringe.fnum])!=true) { //when fringe is not visited
					if(heapfringe.contains(g.members[fringe.fnum])==false) {
						heapfringe.add(g.members[fringe.fnum]);
					}
					
					if(weightnames.contains(g.members[fringe.fnum])) {
						if(weights.get(weightnames.indexOf(g.members[fringe.fnum]))>=prev+1) {
							weights.set(weightnames.indexOf(g.members[fringe.fnum]),prev+1);
						}
					}
					else {
						weightnames.add(g.members[fringe.fnum]);
						weights.add(prev+1);
					}
					heapupdate(heapfringe, weights, weightnames, weights.get(weightnames.indexOf(g.members[fringe.fnum])));
				}
				fringe = fringe.next;
			}
			//for (int i=0;i<heapfringe.size();i++) {
			//	System.out.print(heapfringe.get(i).name+", ");
			//}
			System.out.println();
			pivot=heapfringe.remove(0);
			
			if(heapfringe.isEmpty()==false) {
				heapfringe.add(0,heapfringe.remove(heapfringe.size()-1));
				siftdown(heapfringe, weights, weightnames,weights.get(weightnames.indexOf(heapfringe.get(0))));
				
				
				//System.out.print(pivot.name+", "+weights.get(weightnames.indexOf(pivot))+" | ");
				//for (int i=0;i<heapfringe.size();i++) {
				//	System.out.print(heapfringe.get(i).name+", "+weights.get(weightnames.indexOf(heapfringe.get(i)))+" | ");
				//}
				System.out.println();
			}
			else {
				Friend check = pivot.first;
				while(check!=null) {
					if(visited.contains(g.members[check.fnum])==false) {
						further = true;
						break;
					}
					check=check.next;
				}
				
			}
			System.out.print(pivot.name+", "+weights.get(weightnames.indexOf(pivot))+" | ");
			for (int i=0;i<heapfringe.size();i++) {
				System.out.print(heapfringe.get(i).name+", "+weights.get(weightnames.indexOf(heapfringe.get(i)))+" | ");
			}
			System.out.println();
			prev = weights.get(weightnames.indexOf(pivot));
			
		}while(heapfringe.isEmpty()==false|| further==true);
		
		if(weightnames.contains(g.members[g.map.get(p2)])==false){
			System.out.println("not in same chain");
			return null;
		}
		Person end = g.members[g.map.get(p2)];
		Stack <Person> shortest = new Stack<Person>();
		shortest.push(end);
		while (end!=start) {
			System.out.println(shortest.peek().name);
			Friend fri = end.first;
			Friend minfri = end.first;
			int min = weights.get(weightnames.indexOf(g.members[fri.fnum]));
			do{
				if(min>weights.get(weightnames.indexOf(g.members[fri.fnum]))) {
					min = weights.get(weightnames.indexOf(g.members[fri.fnum]));
					minfri = fri;
				}
				fri = fri.next;
			}while(fri!=null);
			shortest.push(g.members[minfri.fnum]);
			end = g.members[minfri.fnum];
		}
		ArrayList<String> network = new ArrayList<String>();
		while(shortest.isEmpty()==false) {
			network.add(shortest.pop().name);
		}
		System.out.println(network);
		return network;
		*/
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		Person[] mem = new Person [g.members.length]; 
		System.arraycopy(g.members, 0, mem, 0, g.members.length);
		ArrayList<ArrayList<String>> cliq = new ArrayList<ArrayList<String>> ();
		ArrayList<Person> visited = new ArrayList<Person>();
		int counter=0;
		for (int i =0;i<mem.length;i++) { //go through array that has all members
			if(mem[i]==null) { //if entry is null, person has already been visited
				continue;
			}
			else if(mem[i].student==false) { //if they are not a student we aren't interested
				mem[i]= null;
				continue;
			}
			else {
				if(mem[i].school.equals(school)) {	// if they are from target school
					cliq.add(new ArrayList<String>());
					Queue<Person> neighbors = new Queue<Person>();
					neighbors.enqueue(mem[i]);
					Person curr;
					do {
						curr = neighbors.dequeue();
						if(cliq.get(counter).contains(curr.name)==false) {
							cliq.get(counter).add(curr.name);
						}
						visited.add(curr);
						mem[g.map.get(curr.name)]=null;
						Friend f = curr.first;
						while ( f!=null) {
							if(g.members[f.fnum].student==true && visited.contains(g.members[f.fnum])==false && (g.members[f.fnum].school).equals(school)==true ) {
								//System.out.println(g.members[f.fnum].name);
								neighbors.enqueue(g.members[f.fnum]);
							}
							
							f= f.next;
						}
						
						//System.out.println(cliq);
					}while(neighbors.isEmpty()==false);
					counter++;
				}
				else { //if they aren't from target school we don't care about them
					mem[i]=null;
					continue;
				}
			}
		}
		System.out.println("Cliques are: ");
		System.out.println(cliq);
		return cliq;
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		
		Person[] mem = new Person [g.members.length]; 
		int [] dfsnum = new int[mem.length];
		int [] back = new int[mem.length];
		System.arraycopy(g.members, 0, mem, 0, g.members.length);
		ArrayList<String> connector = new ArrayList<String> ();
		ArrayList<Person> visited = new ArrayList<Person>();
		
		for (int i =0;i<mem.length;i++) { //go through array that has all members
			if(mem[i]==null) { //if entry is null, person has already been visited
				continue;
			}
			ArrayList<String> temp = dfsprocess(mem[i], mem[i], dfsnum, back,visited, new ArrayList<String>(),g);
			for(int j=0;j<temp.size();j++) {	//adds to connectors
				connector.add(temp.get(j));
			}
			while(visited.isEmpty()==false) { // removes visited from members array
				mem[g.map.get(visited.remove(0).name)]=null;
			}
		}
		System.out.println("Connectors: ");
		System.out.println(connector);
		return connector;
	}
	private static ArrayList<String> dfsprocess(Person start, Person curr, int [] dfsnum, int[] back, ArrayList<Person> visited, ArrayList<String> con,Graph g){
		Friend remain = curr.first;
		int count =0;
		
		while(remain!=null) {
			if(visited.contains(curr)==false) {
				visited.add(curr);
			}
			if(visited.contains(g.members[remain.fnum])) {
				back[g.map.get(curr.name)]= Math.min(dfsnum[g.map.get(g.members[remain.fnum].name)],back[g.map.get(curr.name)]);
				remain = remain.next; // go through all nodes 
				continue;
			}
			else {
				if(curr==start) {
					count++;
				}
				dfsnum[g.map.get(g.members[remain.fnum].name)]= visited.size();
				back[g.map.get(g.members[remain.fnum].name)]= dfsnum[g.map.get(g.members[remain.fnum].name)];
				con = dfsprocess(start, g.members[remain.fnum], dfsnum, back, visited, con,g);
			}
			if ( dfsnum[g.map.get(curr.name)]> back[g.map.get(g.members[remain.fnum].name)]) {
				back[g.map.get(curr.name)]= Math.min(back[g.map.get(g.members[remain.fnum].name)],back[g.map.get(curr.name)]);
			}
			else if (con.contains(curr.name)==false ) {
				if(curr!=start) {con.add(curr.name);}
				else if (curr==start && count>1) {
					con.add(curr.name);
				}
			}
			remain = remain.next;
		}
		//System.out.println(count);
		return con;
	}
	
}


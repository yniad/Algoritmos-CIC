import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Graph {
	
	boolean dirigido;
	private HashMap<Integer, Set<Integer>> nodes = new HashMap<>();

	
	/**
	 * Constructor sin par�metros, por default es un grafo no dirigido.
	 */
	public Graph() {
		dirigido=false;
	}
	
	/**
	 * Constructor que define si el grafo es dirigido o no
	 * @param dirigido	si el valor es true, ser� un grafo dirigido
	 */
	public Graph(boolean dirigido) {
		this.dirigido=dirigido;
	}
	
	/**
	 * Funci�n para a�adir un nodo
	 * @param id	recibe el identificador del nodo
	 */
	public void addNode(int id) {
		if (!nodes.containsKey(id)) {
			nodes.put(id,new HashSet<Integer>());
		}
	}
	
	
	/**
	 * Funci�n para a�adir un nodo con un vecindario
	 * @param id	Identificador del nodo a a�adir
	 * @param neighbors	Conjunto con los dentificadores de sus nodos vecinos
	 */
	public void addNode(int id,Set<Integer> neighbors) {
		nodes.put(id,neighbors);
		for(Integer n:neighbors) {
			linkNodes(id, n);
		}
	}
	
	/**
	 * Funci�n para eliminar un nodo
	 * @param id	Ientificador del nodo a eliminar
	 */
	public void dropNode(int id) {
		nodes.remove(id);
	}
	
	
	/**
	 * Funci�n para obtener los nodos vecinos de un determinado nodo
	 * @param id	Identificador del nodo del cual se desean conocer sus vecinos
	 * @return
	 */
	public Set<Integer> getNeighbors(int id) {
		return nodes.get(id);
	}
	
	/**
	 * Funci�n para enlazar dos nodos
	 * @param a	identificador del primer nodo
	 * @param b	identificador del segundo nodo
	 * @return	retorna true si los nodos no estaban unidos previamente, en caso de que ya estuvieran unidos retorna falso
	 */
	public boolean linkNodes(int a, int b) {
		if (!nodes.containsKey(a)  ) {
			this.addNode(a);
		}
		if (!nodes.containsKey(b)) {
			this.addNode(b);
		}
		if(dirigido) {
			return nodes.get(a).add(b);
		}else {
			nodes.get(a).add(b);
			return nodes.get(b).add(a);
		}
		
	}
	

	/**
	 * Funci�n para guardar el nodo en un archivo
	 * @param filename	Nombre del archivo en el que se guardar� (incluye la extensi�n)
	 */
	public void saveFile(String filename) {
		try {
		      FileWriter myWriter = new FileWriter(filename);
		      myWriter.write(this.toGrahpViz());
		      myWriter.close();
		      System.out.println("Grafo guardado correctamente como "+filename);
		    } catch (IOException e) {
		      System.out.println("Ha ocurrido un error al guardar el grafo: "+filename);
		      e.printStackTrace();
		    }
	}
	
	
	/**
	 * Funci�n para generar un grafo usando el modelo Erdos Renyi
	 * @param n	N�mero de nodos del grafo
	 * @param m	Nmero de aristas del grafo
	 * @param dirigido	si es true crea un grafo dirigido, de lo contrario es no dirigido
	 * @param r	Objeto random, en caso de que se desee especificar con una semilla.
	 * @return	Grafo
	 */
	public static Graph genErdosRenyi(int n,int m,boolean dirigido,Random r) {
		if (r==null) r=new Random();
		Graph g=new Graph(dirigido);
		for(int i=0;i<n;i++) { //for para crear los n nodos
			g.addNode(i);
		}
		int i=0; //variable que lleva el conteo de las aristas
		int j=0; //variable auxiliar para seleccionar un nodo al azar
		int k=0; //variable auxiliar para seleccionar un nodo al azar
		while(i<m) { //bucle para obtener las m aristas
			j=r.nextInt(n);
			k=r.nextInt(n);
			while(j==k) { //verifica que sean nodos diferentes j y k
				k=r.nextInt(n);
			}
			if(g.linkNodes(j,k)) {//comprueba si los nodos ya estaban unidos
				i++; //en caso de que sea nueva la union agrega 1 al conteo de aristas
			}
			
		}
		return g;
	}
	
	/**
	 * Funci�n para generar un grafo usando el modelo Gilbert
	 * @param n	N�mero de nodos del grafo
	 * @param p	Probabilidad de union entre nodos
	 * @param dirigido	si es true crea un grafo dirigido, de lo contrario es no dirigido
	 * @param r	Objeto random, en caso de que se desee especificar con una semilla.
	 * @return	Grafo
	 */
	public static Graph genGilbert(int n,double p,boolean dirigido,Random r) {
		if (r==null) r=new Random();
		Graph g=new Graph(dirigido);
		for(int i=0;i<n;i++) {
			g.addNode(i);
		}
		double k=0;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(i!=j) {
					k=r.nextDouble();
					if(k<p) {
						g.linkNodes(i,j);
					}
				}
			}
		}
		return g;
	}
	
	/**
	 * Funci�n para generar un grafo usando el modelo geogr�fico simple
	 * @param n	Numero de nodos del gr�fo
	 * @param d	distancia m�nima para union de nodos
	 * @param dirigido	si es true crea un grafo dirigido, de lo contrario es no dirigido
	 * @param r	Objeto random, en caso de que se desee especificar con una semilla.
	 * @return	Grafo
	 */
	public static Graph genGeografico(int n,double d,boolean dirigido,Random r) {
		if (r==null) r=new Random();
		Graph g=new Graph(dirigido);
		Point2D[] p= new Point2D[n];
		for(int i=0;i<n;i++) {
			g.addNode(i);
			p[i]= new Point2D.Double(r.nextDouble(),r.nextDouble());
		}
		float k=0;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(i!=j) {
					if(p[i].distance(p[j])<d) {
						g.linkNodes(i,j);
					}
				}
			}
		}
		return g;
	}
	
	
	/**
	 * Funci�n para generar un grafo usando el modelo Barabasi.
	 * Nota: En mis intentos por generar la variante, realic� este sin querer
	 * por lo que decid� mantenerlo en el c�digo.
	 * @param n	Numero de nodos
	 * @param d	Grado m�ximo de los nodos
	 * @param dirigido	si es true crea un grafo dirigido, de lo contrario es no dirigido
	 * @param r	Objeto random, en caso de que se desee especificar con una semilla.
	 * @return	Grafo
	 */
	public static Graph genBarabasiAlbert(int n,double d,boolean dirigido,Random r) {
		if (r==null) r=new Random();
		Graph g=new Graph(dirigido);
		List<Integer> intList = new ArrayList<Integer>();
		
		int i,j,k,l;
		for(i=0;i<n;i++) {
			g.addNode(i);
			
		}
		intList.add(0);
		for(i=1;i<d;i++) {
			intList.add(i);
			g.linkNodes(i,i-1);
		}
		
		for(;i<n;i++) {
			for(j=0;j<intList.size();j++) {
				l=intList.get(j);
				k=g.nodes.get(l).size();
				if(r.nextDouble()<k/d) {
					if(g.linkNodes(i,l)) {
						if( k==d-1) {
							intList.remove(j);
							j--;
						}
						break;
					}
				}
				
			}
			intList.add(i);
			if(g.nodes.get(i).size()==0) { //en caso de que el nodo no se haya conectado, se fuerza a que tenga almenos un enlace
				BarabasiVariantEnforceLink(intList,d,g,r);
			}
			
		}
		return g;
	}
	
	/**
	 * Funci�n para generar un grafo usando el modelo Barabasi.
	 * Nota: a diferencia de la funci�n anterior, se seleccionan los nodos de manera aleatoria
	 * @param n	Numero de nodos
	 * @param d	Grado m�ximo de los nodos
	 * @param dirigido	si es true crea un grafo dirigido, de lo contrario es no dirigido
	 * @param r	Objeto random, en caso de que se desee especificar con una semilla.
	 * @return	Grafo
	 */
	public static Graph genBarabasiAlbertRandom(int n,double d,boolean dirigido,Random r) {
		if (r==null) r=new Random();
		Graph g=new Graph(dirigido);
		List<Integer> intList = new ArrayList<Integer>();
		
		int i,j,k,l;
		for(i=0;i<n;i++) {
			g.addNode(i);
			
		}
		intList.add(0);
		for(i=1;i<d;i++) {
			intList.add(i);
			j=i-1;
			g.linkNodes(i,j);
		}
		
		for(;i<n;i++) {
			Collections.shuffle(intList);
			for(j=0;j<intList.size();j++) {
				l=intList.get(j);
				k=g.nodes.get(l).size();
				if(r.nextDouble()<k/d) {
					if(g.linkNodes(i,l)) {
						if( k==d-1) {
							intList.remove(j);
							j--;
						}
						break;
					}
				}
				
			}
			intList.add(i);
			if(g.nodes.get(i).size()==0) { //en caso de que el nodo no se haya conectado, se fuerza a que tenga almenos un enlace
				BarabasiVariantEnforceLink(intList,d,g,r);
			}
			
		}
		return g;
	}
	
	/**
	 * Funci�n para generar un grafo usando la variante del modelo Barabasi
	 * @param n	Numero de nodos
	 * @param d	Grado m�ximo de los nodos
	 * @param dirigido	si es true crea un grafo dirigido, de lo contrario es no dirigido
	 * @param r	Objeto random, en caso de que se desee especificar con una semilla.
	 * @return	Grafo
	 */
	public static Graph genBarabasiAlbertVariant(int n,double d,boolean dirigido,Random r) {
		if (r==null) r=new Random();
		Graph g=new Graph(dirigido);
		List<Integer> intList = new ArrayList<Integer>();
		int i,j,k,l;
		for(i=0;i<n;i++) {
			g.addNode(i);
		}
		intList.add(0);
		for(i=1;i<d;i++) {
			intList.add(i);
			j=i-1;
			g.linkNodes(i,j);
			
		}
		
		for(;i<n;i++) {
			for(j=0;j<intList.size();j++) {
				l=intList.get(j);
				k=g.nodes.get(l).size();
				if( k<d) {
					if(r.nextDouble()<k/d) {
						if(g.linkNodes(i,l)) {
							if(g.nodes.get(l).size()==d) {
								intList.remove(j);
								j--;
							}
							if(g.nodes.get(i).size()==(d-1)) { //Con esta condicion se asegura que el siguiente nodo tendr� un espacio libre con quien conectarse
								break;
							}
						}
					}
				}
			}
			intList.add(i);
			if(g.nodes.get(i).size()==0) { //en caso de que el nodo no se haya conectado, se fuerza a que tenga almenos un enlace
				BarabasiVariantEnforceLink(intList,d,g,r);
			}
			
		}
		return g;
	}
	
	
	/**
	 * Funci�n para generar un grafo usando la variante del modelo Barabasi, escogiendo los nodos de manera aleatoria
	 * @param n	Numero de nodos
	 * @param d	Grado m�ximo de los nodos
	 * @param dirigido	si es true crea un grafo dirigido, de lo contrario es no dirigido
	 * @param r	Objeto random, en caso de que se desee especificar con una semilla.
	 * @return	Grafo
	 */
	public static Graph genBarabasiAlbertVariantRandom(int n,double d,boolean dirigido, Random r) {
		if (r==null) r=new Random();
		Graph g=new Graph(dirigido);
		List<Integer> intList = new ArrayList<Integer>();
		int i,j,k,l;
		for(i=0;i<n;i++) {
			g.addNode(i);
		}
		intList.add(0);
		for(i=1;i<d;i++) {
			intList.add(i);
			for(j=0;j<i;j++) {
				g.linkNodes(i,j);
			}
		}
		
		for(;i<n;i++) {
			Collections.shuffle(intList);
			for(j=0;j<intList.size();j++) {
				l=intList.get(j);
				k=g.nodes.get(l).size();
				if(r.nextDouble()<k/d) {
					if(g.linkNodes(i,l)) {
						if(k==(d-1)) {
							intList.remove(j);
							j--;
						}
						if(g.nodes.get(i).size()==(d-1)) { //Con esta condicion se asegura que el siguiente nodo tendr� un espacio libre con quien conectarse
							break;
						}
					}
				}
			}
			intList.add(i);
			if(g.nodes.get(i).size()==0) { //en caso de que el nodo no se haya conectado, se fuerza a que tenga almenos un enlace
				BarabasiVariantEnforceLink(intList,d,g,r);
			}
			
		}
		return g;
	}
	
	/**
	 * Funci�n auxiliar del modelo barabasi para forzar un enlace
	 * @param intList	Lista con los nodos de grado menor a d
	 * @param d	Grado m�ximo de los nodos
	 * @param g Grafo en el cual se unen los nodos
	 * @param r	Objeto Random para conservar la semilla usada en la funci�n que lo llama
	 */
	private static void BarabasiVariantEnforceLink(List<Integer> intList,double d,Graph g, Random r) {
		int i,j,k,l;
		i=intList.size()-1;
		while(true) {
			for(j=0;j<i;j++) {
				l=intList.get(j);
				k=g.nodes.get(l).size();
				if(r.nextDouble()<k/d) {
					if(g.linkNodes(intList.get(i),l)) {
						if(k==(d-1)) {
							intList.remove(j);
						}
						return;
					}
				}
				
			}
		}
	}
	
	/**
	 * funcion que retorna un String con el formato GraphViz 
	 * @return String con formato de graph viz
	 */
	public String toGrahpViz() {
		String res="";
		String arista;
		for ( Integer node : nodes.keySet() ) {
			res+="  "+node+";\n";
		}
		if(dirigido) {
			arista=" -> ";
			res="digraph {\n"+res;
			for (Map.Entry<Integer,Set<Integer>> n : nodes.entrySet()) {
				Integer node=n.getKey();
				for (Integer value : n.getValue()) {
					res+="  "+node+arista+value+";\n";
				}
		    }
		}else {
			arista=" -- ";
			res="graph {\n"+res;
			for (Map.Entry<Integer,Set<Integer>> n : nodes.entrySet()) {
				Integer node=n.getKey();
				for (Integer value : n.getValue()) {
					if (node<value) {
						res+="  "+node+arista+value+";\n";
					}
				}
		    }
		}
		
		res+="}";
		return res;
	}
}
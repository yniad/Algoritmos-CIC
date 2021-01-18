

public class Index {
	
	public static void parte_uno() {
		
		String path="gv/";
		Graph g;
		
		
		g= Graph.genErdosRenyi(30, 30,false,null);
		g.saveFile(path+"erdosRenyi_30.gv");
		
		g= Graph.genErdosRenyi(100, 100,false,null);
		g.saveFile(path+"erdosRenyi_100.gv");
		
		g= Graph.genErdosRenyi(500, 500,false,null);
		g.saveFile(path+"erdosRenyi_500.gv");
		
		g= Graph.genGilbert(30, 0.05,false,null);
		g.saveFile(path+"gilbert_30.gv");
		
		g= Graph.genGilbert(100, 0.01,false,null);
		g.saveFile(path+"gilbert_100.gv");
		
		g= Graph.genGilbert(500, 0.002,false,null);
		g.saveFile(path+"gilbert_500.gv");
		
		g= Graph.genGeografico(30, 0.2,false,null);
		g.saveFile(path+"geografico_30.gv");
		
		g= Graph.genGeografico(100, 0.15,false,null);
		g.saveFile(path+"geografico_100.gv");
		
		g= Graph.genGeografico(500, 0.1,false,null);
		g.saveFile(path+"geografico_500.gv");
		
		g= Graph.genBarabasiAlbertVariantRandom(30, 5,false,null);
		g.saveFile(path+"barabasi_30.gv");
		
		g= Graph.genBarabasiAlbertVariantRandom(100, 5,false,null);
		g.saveFile(path+"barabasi_100.gv");
		
		g= Graph.genBarabasiAlbertVariantRandom(500, 5,false,null);
		g.saveFile(path+"barabasi_500.gv");
		
		
	}
	
	
	public static void parte_dos() {
		int raiz=0;
		String path="gv/";
		
		Graph a,b,c,d;
		
		String[] files = new String[]{
				"erdosRenyi",
				"gilbert",
				"geografico",
				"barabasi"
				};
		Integer[] nodes_number= new Integer[] {30,100,500};
		
		for (String tipo : files) {
			for (Integer number : nodes_number) {
				String filename=tipo+"_"+number+".gv";
				a = Graph.loadFile(path+filename);
				b=a.getDFSr(raiz);
				c=a.getDFSi(raiz);
				d=a.getBFS(raiz);
				
				b.saveFile(path+"arbol_DFSr_"+filename);
				c.saveFile(path+"arbol_DFSi_"+filename);
				d.saveFile(path+"arbol_BFS_"+filename);
				
			}
		}
		
	}
	
	public static void parte_tres() {
		String path="gv/";
		Graph g;
		
		boolean dirigido=false;
		
		
		String[] files = new String[]{
				"w_erdosRenyi",
				"w_gilbert",
				"w_geografico",
				"w_barabasi"
				};
		Integer[] nodes_number= new Integer[] {30,100,500};
		
		
		g= Graph.genErdosRenyi(nodes_number[0], 30,dirigido,null);
		g.saveFile(path+files[0]+"_"+nodes_number[0]+".gv");
		
		g= Graph.genErdosRenyi(nodes_number[1], 100,dirigido,null);
		g.saveFile(path+files[0]+"_"+nodes_number[1]+".gv");
		
		g= Graph.genErdosRenyi(nodes_number[2], 500,dirigido,null);
		g.saveFile(path+files[0]+"_"+nodes_number[2]+".gv");
		
		g= Graph.genGilbert(nodes_number[0], 0.05,dirigido,null);
		g.saveFile(path+files[1]+"_"+nodes_number[0]+".gv");
		
		g= Graph.genGilbert(nodes_number[1], 0.01,dirigido,null);
		g.saveFile(path+files[1]+"_"+nodes_number[1]+".gv");
		
		g= Graph.genGilbert(nodes_number[2], 0.002,dirigido,null);
		g.saveFile(path+files[1]+"_"+nodes_number[2]+".gv");
		
		g= Graph.genGeografico(nodes_number[0], 0.2,dirigido,null);
		g.saveFile(path+files[2]+"_"+nodes_number[0]+".gv");
		
		g= Graph.genGeografico(nodes_number[1], 0.15,dirigido,null);
		g.saveFile(path+files[2]+"_"+nodes_number[1]+".gv");
		
		g= Graph.genGeografico(nodes_number[2], 0.1,dirigido,null);
		g.saveFile(path+files[2]+"_"+nodes_number[2]+".gv");
		
		g= Graph.genBarabasiAlbertVariantRandom(nodes_number[0], 5,dirigido,null);
		g.saveFile(path+files[3]+"_"+nodes_number[0]+".gv");
		
		g= Graph.genBarabasiAlbertVariantRandom(nodes_number[1], 5,dirigido,null);
		g.saveFile(path+files[3]+"_"+nodes_number[1]+".gv");
		
		g= Graph.genBarabasiAlbertVariantRandom(nodes_number[2], 5,dirigido,null);
		g.saveFile(path+files[3]+"_"+nodes_number[2]+".gv");
		

		
	}
	
	public static void parte_tres_dijkstra() {
		String path="gv/";
		String start="w_";
		
		int raiz=0;
		Graph a,b;
		
		String[] files = new String[]{
				start+"erdosRenyi",
				start+"gilbert",
				start+"geografico",
				start+"barabasi"
				};
		Integer[] nodes_number= new Integer[] {30,100,500};
		
		
		for (String tipo : files) {
			for (Integer number : nodes_number) {
				String filename=tipo+"_"+number+".gv";
				a = Graph.loadFile(path+filename);
				b=a.getDijkstra(raiz);
				b.saveFile(path+"D_"+raiz+"_"+filename.replace(start,""));
				
			}
		}
	}

	
	public static void parte_4_kruskal(){
		String path="gv/";
		String start="w_";
		
		int raiz=0;
		Graph a,b;
		
		String[] files = new String[]{
				start+"erdosRenyi",
				start+"gilbert",
				start+"geografico",
				start+"barabasi"
				};
		Integer[] nodes_number= new Integer[] {
				30,
				100,
				500};
		
		
		for (String tipo : files) {
			for (Integer number : nodes_number) {
				String filename=tipo+"_"+number+".gv";
				a = Graph.loadFile(path+filename);
				b=a.getKruskal(raiz);
				b.saveFile(path+"K_"+raiz+"_"+filename.replace(start,""));
				
			}
		}
	}
	
	public static void parte_4_kruskal_invertido(){
		String path="gv/";
		String start="w_";
		
		int raiz=0;
		Graph a,b;
		
		String[] files = new String[]{
				start+"erdosRenyi",
				start+"gilbert",
				start+"geografico",
				start+"barabasi"
				};
		Integer[] nodes_number= new Integer[] {
				30,
				100,
				500};
		
		
		for (String tipo : files) {
			for (Integer number : nodes_number) {
				String filename=tipo+"_"+number+".gv";
				a = Graph.loadFile(path+filename);
				b=a.getKruskal_i(raiz);
				b.saveFile(path+"Ki_"+raiz+"_"+filename.replace(start,""));
				
			}
		}
	}
	
	public static void parte_4_prim() {
		String path="gv/";
		String start="w_";
		
		int raiz=0;
		Graph a,b;
		
		String[] files = new String[]{
				start+"erdosRenyi",
				start+"gilbert",
				start+"geografico",
				start+"barabasi"
				};
		Integer[] nodes_number= new Integer[] {
				30,
				100,
				500};
		
		
		for (String tipo : files) {
			for (Integer number : nodes_number) {
				String filename=tipo+"_"+number+".gv";
				a = Graph.loadFile(path+filename);
				b=a.getKruskal_i(raiz);
				b.saveFile(path+"P_"+raiz+"_"+filename.replace(start,""));
				
			}
		}
	}
	
	public static void main(String[] args) {
		parte_4_prim();
	}

}

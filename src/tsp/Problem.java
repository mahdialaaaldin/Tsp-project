package tsp;

import java.io.*;
import java.util.*;



public class Problem
{
	
    public static final int MAX_ITERATIONS=1000; 
    public static final double MutationRate=0.05;
    public static final double CrossOverRate=1;
      
    public static final int POPULATION=25;
    public static final int InitialSolution =2 ; // 1 --> random, 2--> by nearest neighbor
    
    private String path;
    double [][] NN;  
    ArrayList<Node> Nodes=new ArrayList<>();
    int nb_Nodes;
   

    public Problem(String file_Path)
    {
        this.path=file_Path;
        try {
            fromFile();
            System.out.println("----- Finish loading file---------------");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        calcul_distances();
  }

   
 
    
    private void calcul_distances() {
        this.NN =new double[this.nb_Nodes][this.nb_Nodes];

        for (int i=0;i<nb_Nodes;i++)
        {
            for (int j=0;j<nb_Nodes;j++)
            {         	
                NN[i][j]=distance(Nodes.get(i), Nodes.get(j));
            }
        }
        System.out.println("----- Finish constructing of nodes NN nodes matrix---------------");
    }


    double distance (Node n1, Node n2) {
        double deltaX = n1.x - n2.x;
        double deltaY = n1.y - n2.y;
        
        return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    }
    
    private void fromFile() throws FileNotFoundException
    {
    	File f=new File(path);
        Scanner sc=new Scanner(new FileInputStream(f));

        String s;
        String[] line;
        
		// Loading of number of nodes
        while(true)
        	{
                s=sc.nextLine();
        	line=s.split("\\s+");
        	if(line[0].equals("DIMENSION"))
        		this.nb_Nodes=Integer.parseInt(line[2]);
        	else if(line[0].equals("NODE_COORD_SECTION"))
        		break;
        	else 
        		continue;
        	}
        
        // loading the nodes
        for(int i=0;i<this.nb_Nodes;i++)
        {
        	s=sc.nextLine();
        	line=s.split("\\s+");
        	Node n = new Node(Integer.parseInt(line[0]), Double.parseDouble(line[1]), Double.parseDouble(line[2]));
        	this.Nodes.add(n);
        }
        
      
        //System.out.println("--------------");
            sc.close();
    }
  
}




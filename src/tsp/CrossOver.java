package tsp;

import java.util.ArrayList;
import java.util.Random;

public class CrossOver {
	
    Problem problem;  
    ArrayList<Solution> population;
    Random r=new Random();
    int startPoint,endPoint;
	    
    public CrossOver(Problem problem, ArrayList<Solution> population) {
        this.problem = problem;
        this.population = population;
    }

    public Solution singlePointCrossover(Solution p1 , Solution p2)
    {
    	Solution child = new Solution(problem);
    	/* code */
    	startPoint = r.nextInt(p1.path.size());
        
        for(int i=0;i<startPoint;i++){
            child.path.add(p1.path.get(i));
        }
        for(int j=0;j<p1.path.size();j++){
            if(!child.path.contains(p2.path.get(j)))
                child.path.add(p2.path.get(j));
            
            if(child.path.size()==p1.path.size())
                break;   
        }
     return child;
    }
    
	
	public Solution twoPointsCrossover(Solution p1 , Solution p2)
    {
    	Solution child = new Solution(problem);
    	/* code */
        int r1 = r.nextInt(p1.path.size());
        int r2 = r.nextInt(p1.path.size());
        
       if(r1<r2){
           startPoint=r1;
           endPoint=r2;
       }
       else
       {
           startPoint=r2;
           endPoint=r1;
           
       }
        
        for(int i=0;i<startPoint;i++){
            child.path.add(p1.path.get(i));
        }
        
        for(int j=0;j<p2.path.size();j++){
            
            if(child.path.size()==endPoint)
                break;  
            
            if(!child.path.contains(p2.path.get(j)))
                child.path.add(p2.path.get(j));   
        }
        
        for(int k=startPoint;k<p1.path.size();k++){
            
            if(!child.path.contains(p1.path.get(k)))
                child.path.add(p1.path.get(k));
            
            if(child.path.size()==p1.path.size())
                break;  
        }
        
    	 return child;
    }
	  
     
        public Solution OXCrossover(Solution p1 , Solution p2)
    {
        
    	Solution child = new Solution(problem);
    	
        ArrayList <Node> a=new ArrayList<>();
        ArrayList <Node> b=new ArrayList<>();
        
        
        Node n = new Node(-1,-1,-1);
        
        
        for(int i=0;i<p1.path.size();i++){
            child.path.add(i,n);
        }
          
        int NbIndices= r.nextInt(p1.path.size());
        ArrayList<Integer>indices=new ArrayList<>();
        
        for(int j=0;j<NbIndices;j++){
           int indice=r.nextInt(p1.path.size());

           while(indices.contains(indice)){
               indice=r.nextInt(p1.path.size());   
           }
           child.path.set(indice,p1.path.get(indice));
           a.add(p1.path.get(indice));
        }
          
        for(int i=0;i<p2.path.size();i++){
            b.add(p2.path.get(i));
        }
        b.removeAll(a);

        int i=0;
        for(int j =0 ;j<child.path.size();j++){
            if(child.path.get(j).equals(n))
            {
                child.path.set(j,b.get(i++));
            }
        }


       return child;
    }
	
}

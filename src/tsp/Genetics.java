package tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genetics
{
    Problem problem;  
    ArrayList<Solution> population;
    Random r=new Random();
    double rand;
    Solution parent1,parent2;
    Solution child1,child2;
    double child1_cost,child2_cost;
    ArrayList<Solution> new_population; 
    double [] prob;
    
    public Genetics(Problem problem)
    {
        this.problem=problem;     
        population=new ArrayList<>();
   
        generateInitialPopulation();
        Collections.sort(population);
        System.out.println("Initial population");
       
        System.out.println("Initial solution \n "+ population.get(0).toString());
        System.out.println("Initial Cost " + population.get(0).getTotal_Cost()+ " Size: " + population.get(0).path.size());
        System.out.println("*****************************************************************************************");
   }
    
    
    private void generateInitialPopulation()
    {
        switch (problem.InitialSolution)
        {
            case 1:
                generateInitialRandomPopulation();
                break;
            case 2:
                generateInitialNearestPopulation();
                break;
        }
    	 
    }
    
    private void generateInitialRandomPopulation()
    {
        Solution s;
        int i,j;
        s=generateRandomsolution();
        population.add(s);

        for(i=1;i<problem.POPULATION;i++)
        { 
           s=generateRandomsolution();
            // Check if the generated solution already exists in the population
           for(j=0;j<i;j++)
        { 
               if(population.get(j).total_Cost==s.total_Cost)
                    break;
               if(population.get(j).equals(s))
                    break;

            }
            if (j<i)
            {
                i--;
                continue;
            }
           population.add(s);


        }

        Collections.sort(population);
             
    }
    
    
    public Solution generateRandomsolution()
    {	
        Solution s = new Solution(problem);
        ArrayList<Node> temp= new ArrayList<>(problem.Nodes);

        Random r = new Random();
        int rand;
        Node current;
        rand= r.nextInt(temp.size());
            current=temp.remove(rand);
            s.add_Node(current);

        while(temp.size()!=0)
        {
            rand= r.nextInt(temp.size());
            current=temp.remove(rand);

            s.add_Node(current);
        }

        s.inc_Cost(s.computeCost());
        return s;
    }
    

   public Solution generateNearestInitialsolution()
    {
        Solution s = new Solution(problem);
        ArrayList<Integer>visit= new ArrayList<>();
        double[][] dist = problem.NN;
        Random r = new Random(); 
        int x=r.nextInt(problem.Nodes.size());
        int i;
        while(true){
            int index=0;
            double m=10000;
            Node current=new Node(x, problem.Nodes.get(x).getX(), problem.Nodes.get(x).getY());
            for(i=0;i<problem.Nodes.size();i++){
                if(x==i)
                    continue;
                
                if(dist[x][i]<m && !visit.contains(i)){
                    m=dist[x][i];
                    index=i;
                    
                }
            }
            visit.add(x);
            x=index;
            s.path.add(current);
            if(s.path.size()== problem.Nodes.size())
                break;
        }
        
       return s;
    }

    private void generateInitialNearestPopulation()
    {
    	Solution s;
        int i,j;
        s=generateNearestInitialsolution();
        s.inc_Cost(s.computeCost());
        population.add(s);

        for(i=1;i<problem.POPULATION;i++)
        { 
            s=generateNearestInitialsolution();
            s.inc_Cost(s.computeCost());
            // Check if the generated solution already exists in the population
            for(j=0;j<i;j++)
            { 
                if(population.get(j).total_Cost==s.total_Cost)
                    break;
                if(population.get(j).equals(s))
                    break;

            }
            if (j<i)
            {
                i--;
                continue;
            }
            
           population.add(s);


        }

        Collections.sort(population);
    }
    
    



    // mode 1--> random, 2--> roulette, ... 
    // n number of parents to be selected
    private int [] nextParents(ArrayList<Solution> rest,int mode, int n)
    {
        int [] indexes=new int[n];
       
        int i,j,r1;
        switch (mode)
        {
            case 1:
            	indexes[0]=r.nextInt(rest.size());
            	for(i=1;i<n;i++)
            	{
                    r1=r.nextInt(rest.size());
                    for(j=0;j<i;j++)
                    {
                        if(r1==indexes[j])
                             break;
                    }
                    if(j==i)
                       indexes[i]=r1;
                    else i--;
                }
                break;

            case 2:
                
            	// Code roulette
                prob=rouletteWheelSelection(population);
                double roulette = 0;
                for(int b =0;b<n;b++){
                    roulette = (double)(Math.random());
                    double sumproba=0;
                    for(int a=0;a<n;a++){
                        if(roulette<sumproba+prob[a]){
                            indexes[b]=a;
                            break;
                        }
                        sumproba+=prob[a];
                    }
                }
            	break;
        }
        return indexes;
    }

    public  double [] rouletteWheelSelection(ArrayList<Solution>  population)
    {
        double totalSum = 0.0;
        double [] prob=new double[population.size()];

        for(int i=0; i<population.size(); i++)
        {
            Solution s = population.get(i);
            totalSum += s.getTotal_Cost();
        }
      
        for(int i=0; i<population.size(); i++)
        { 
            Solution s = population.get(i);
            prob[i]=s.total_Cost/ totalSum;
        }
        return prob;
    }
      

    public  Solution Start()
    {
    	int[] indexes; // indexes of selected parents
    	ArrayList<Solution> rest=(ArrayList<Solution>)population.clone();
    	
    	CrossOver cross = new CrossOver(problem,rest);
        Mutation mutate = new Mutation(problem);
        
        for (int it=0;it<problem.MAX_ITERATIONS;it++)
        {   
            indexes= nextParents(rest, 2,problem.POPULATION); // 2 roulette
            
                
            /* code crossover, mutation and generation of the next population*/
            for(int i=0;i<indexes.length;i=i+2)
            { 
                
                if(i==indexes.length-1){
                    parent1=rest.get(i);
                    parent2=rest.get(0);
                }
                else{
                    parent1=rest.get(i);
                    parent2=rest.get(i+1);
                }
                
//                child1 = cross.twoPointsCrossover(parent1, parent2);
                child1=cross.OXCrossover(parent1, parent2);
//                child1 = cross.singlePointCrossover(parent1, parent2);
                
//                child2 = cross.twoPointsCrossover(parent2, parent1);
               child2 = cross.OXCrossover(parent2, parent1);
//                child2 = cross.singlePointCrossover(parent2, parent1);
               
                double rangeMin=0;
                double rangeMax=Problem.MutationRate*2;
                double random = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
                
               if(random<Problem.MutationRate)
                    child1=mutate.swap(child1);

                if(random<Problem.MutationRate)
                    child2=mutate.swap(child2);

                child1.inc_Cost(child1.computeCost());
                child2.inc_Cost(child2.computeCost());

                rest.add(child1);
                rest.add(child2);
                
            }

            Collections.sort(rest);
            
            for(int k=population.size();k<population.size()*2;k++)
                rest.remove(rest.size()-1);

            Collections.sort(rest);
        }
        
        Collections.sort(rest);
        
        return rest.get(0);
   }
    


    double distance (Node n1, Node n2) {
        double deltaX = n1.x - n2.x;
        double deltaY = n1.y - n2.y;
        
        return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    }
}


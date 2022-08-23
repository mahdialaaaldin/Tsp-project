package tsp;
import java.util.ArrayList;
import java.util.Iterator;

public class Solution implements Comparable<Solution>
{
    Problem problem;

    ArrayList<Node> path; //Solution
    double total_Cost;
    double temp;

    public Solution(Problem problem)
    {
        this.problem=problem;
        path=new ArrayList<>();
        total_Cost=0;
    }

    //add node to each path
    public void add_Node(Node node)
    {
        path.add(node);
    }
    
//    public boolean contains(Node node){
//        if(path.contains(node))
//            return true;
//        return false;
//    }

    public ArrayList<Node> getPath()
    {
        return path;
    }


    //increase total cost
    public void inc_Cost(double value)
    {
        total_Cost+=value;
    }

    public double getTotal_Cost() {
        return total_Cost;
    }

    @Override
    public int compareTo(Solution solution) {
        if(this.total_Cost>solution.total_Cost)
            return 1;
        if(this.total_Cost==solution.total_Cost)
            return 0;
        return -1;
    }

  
    public double computeCost()
    {
        
        double cost=0, deltaX, deltaY;
        Node n1= this.path.get(0);
        Node n2;
        for(int i=1;i<this.path.size();i++)
        {
     	 n2 = this.getPath().get(i);
         deltaX = n1.x - n2.x;
         deltaY = n1.y - n2.y;
         n1= this.getPath().get(i);
         cost+= Math.sqrt(deltaX*deltaX + deltaY*deltaY);
   
        }
        deltaX = this.getPath().get(this.getPath().size()-1).x - this.getPath().get(0).x;
        deltaY = this.getPath().get(this.getPath().size()-1).y - this.getPath().get(0).y;
        cost+= Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        
        return cost;
    }
    
    @Override
    public String toString()
    {
        String s="";
        Iterator<Node> iterator=path.iterator();
        while (iterator.hasNext())
        {
            s+=" "+iterator.next().getIndex();
        }

                return s;
    }

    @Override
    public boolean equals(Object obj) {
        Solution other = (Solution) obj;
        int i;

        for(i=0;i<this.path.size();i++)
        {
            if(this.path.get(i).equals(other.path.get(i)))
                break;
        }
        if(i<this.path.size())
            return false;
        else 
            return true;
    }
	
    public void copyPath(Solution s)
    {
        this.path.clear();
        for(int i=0;i<s.path.size();i++)
            this.path.add(s.path.get(i));
        this.total_Cost=s.total_Cost;

    }
}
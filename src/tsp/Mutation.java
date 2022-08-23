package tsp;

import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Mutation {
    Problem problem;
    Random r=new Random();
    
    int oneR,twoR;
    Node temp;
	
     
    public Mutation(Problem problem) {
        this.problem = problem;
    }
	    

    Solution swap(Solution s)
    {
        this.problem=s.problem;
        Solution res = new Solution(problem);
                /*code */
                
        res.path=s.path;
       
        oneR = r.nextInt(res.path.size());
        twoR = r.nextInt(res.path.size());

        temp = res.path.get(oneR);
        res.path.set(oneR, res.path.get(twoR));
        res.path.set(twoR, temp);
         
        return res;
    }
	
    Solution reverse(Solution s)
    {
        this.problem=s.problem;
        Solution res = new Solution(problem);
                /*code */
                
        res.path=s.path;
        
        int r1 = r.nextInt(res.path.size());
        int r2 = r.nextInt(res.path.size());
        
        if(r1<r2){
            oneR=r1;
            twoR=r2;
        }
        else
        {
            oneR=r2;
            twoR=r1;

        }
    

        while(oneR<twoR){
            temp=res.path.get(oneR);
            res.path.set(oneR, res.path.get(twoR));
            res.path.set(twoR, temp);

            oneR+=1;
            twoR-=1;
        } 
        return res;
    }
	
}
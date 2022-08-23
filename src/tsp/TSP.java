/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;

/**
 *
 * @author mahdi
 */
public class TSP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String path="C:\\Users\\Mahdi Alaa Aldin\\Documents\\Master\\Semester 2\\INFO435 database system\\Project\\Instances_TSP\\vlsi\\";
        path+="xqf131.tsp";
        
      //  long start=System.currentTimeMillis();
        Problem problem=new Problem(path);
        Genetics gen = new Genetics(problem);
        Solution solution=gen.Start();
        System.out.println("------- Final solution -------- ");
        System.out.println(solution.toString() + "\n cost: " + solution.total_Cost + " Size: " + solution.path.size());
         // System.out.println("Execution Time: "+(System.currentTimeMillis()-start)+" ms");
   
    }
    
}

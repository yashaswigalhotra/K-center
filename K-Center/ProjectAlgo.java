import java.util.Random;
import java.lang.Math; 

import java.io.*;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;  
import java.util.*;
class ProjectAlgo
{
	static Random rand = new Random();
	private static void getSubsets(List<Integer> superSet, int k, int idx, Set<Integer> current,List<Set<Integer>> solution) {
    //successful stop clause
    if (current.size() == k) {
        solution.add(new HashSet<>(current));
        return;
    }
    //unseccessful stop clause
    if (idx == superSet.size()) return;
    Integer x = superSet.get(idx);
    current.add(x);
    //"guess" x is in the subset
    getSubsets(superSet, k, idx+1, current, solution);
    current.remove(x);
    //"guess" x is not in the subset
    getSubsets(superSet, k, idx+1, current, solution);
	}

	public static List<Set<Integer>> getSubsets(List<Integer> superSet, int k) {
    	List<Set<Integer>> res = new ArrayList<>();
	    getSubsets(superSet, k, 0, new HashSet<Integer>(), res);
    	return res;
	}

	public static double get_obj(ArrayList<Integer>cluster_centers,ArrayList<ArrayList<Integer>> clusters,ArrayList<ArrayList<Double>> points){
		double max_radius=-1;
		for (int j=0;j<cluster_centers.size();j++){

			//Farthest stores the farthest point in jth cluster
			int farthest=identify_farthest(clusters.get(j),cluster_centers.get(j),points);
			double dist= (get_distance(points.get(farthest),points.get(cluster_centers.get(j))));
			//Compare the jth cluster farthest with the farthest ofother clusters to get global farthest
			if (dist>max_radius){
				max_radius=dist;
			}

		}
		return max_radius;
	}
	public static double run_optimal_k_center(int N, ArrayList<ArrayList<Double>> points, int k){
			ArrayList<Integer> index_set=new ArrayList<Integer>();
					ArrayList<ArrayList<Integer>> clusters=new ArrayList<ArrayList<Integer>>();

			for (int i=0;i<N;i++)
				index_set.add(i);
			List<Set<Integer>> res =  (getSubsets(index_set,k));

			double min_obj=100;
			ArrayList<Integer> bestnodes=new ArrayList<Integer>();
			for (Set<Integer> center_lst:res){
				//System.out.println(center_lst);
				ArrayList<Integer> cList = new ArrayList<Integer>(k); 
   				 for (int x : center_lst) 
      				cList.add(x);
				clusters=assign_points(cList,points);

				double obj=get_obj(cList,clusters,points);
				if (obj<min_obj){
					min_obj=obj;
					bestnodes=cList;
					//System.out.println("best till now"+min_obj);
				}

			}
			clusters=assign_points(bestnodes,points);
			System.out.println("\n\nCenter selected by optimal algorithm are"+bestnodes);
			System.out.println("Clusters formed by optimal are"+clusters);
			//System.out.println("Optimal objective is "+min_obj);
			return min_obj;


	}
	public static double find_distance(int N, ArrayList<ArrayList<Integer>> aList, ArrayList<Integer> arr)
	{
		int max = 0;
		for(int i=0;i<aList.size();i++)
		{
			ArrayList<Integer> list= aList.get(i);
			int center=arr.get(i);
			for(int j=0;j<list.size();j++)
			{
				if(list.get(j)-center > max)
				{
					max=list.get(j)-center;
				}
			}
		}
		return (double)max;
	}
	public static ArrayList<Double> generate_random_point(int dim){
		ArrayList<Double> coordinates=new ArrayList<Double>();
		for (int i=0;i<dim;i++){
			coordinates.add((double)rand.nextFloat());
		}
		return coordinates;
	}

	public static double get_distance(ArrayList<Double> point1, ArrayList<Double>point2){
		double dist=0;
		for(int i=0;i<point1.size();i++){
			dist = dist + (point1.get(i)-point2.get(i))*(point1.get(i)-point2.get(i));
		}
		return Math.sqrt(dist);

	}
	public static int identify_farthest(ArrayList<Integer> cluster, int center, ArrayList<ArrayList<Double>> points){
		double farthest_dist=0;
		int farthest_point=-1;
		for (int v:cluster){
			//System.out.println(v+" "+center+" "+points.get(v)+" "+points.get(center)+" "+get_distance(points.get(v),points.get(center)));
			if (get_distance(points.get(v),points.get(center))>farthest_dist){
				farthest_dist=get_distance(points.get(v),points.get(center));
				farthest_point=v;
			}
		}
		if (cluster.size()==1){
			return center;
		}
		return farthest_point;
	}
	public static int get_closest_cluster (ArrayList<Double> point, ArrayList<Integer> centers, ArrayList<ArrayList<Double>> points){
		double mindist=10000;
		int closest_center=-1;
		for (int i=0;i<centers.size();i++){
			double dist=get_distance(point,points.get(centers.get(i)));
			if (dist<mindist){
				mindist=dist;
				closest_center=i;
			}

		}
		return closest_center;

	}
	public static ArrayList<ArrayList<Integer>> assign_points(ArrayList<Integer> cluster_centers, ArrayList<ArrayList<Double>> points){


		 ArrayList<ArrayList<Integer>> clusters=new ArrayList<ArrayList<Integer>>();
		 for (int i=0;i<cluster_centers.size();i++){
		 	ArrayList<Integer> tmp=new ArrayList<Integer> ();
		 	clusters.add(tmp);
		 }

		 for (int i=0;i<points.size();i++){

		 	int c=get_closest_cluster(points.get(i),cluster_centers,points);
		 	ArrayList<Integer> cluster=clusters.get(c);
		 	cluster.add(i);
		 	clusters.set(c,cluster);

		 }
		 return clusters;


	}
	public static double run_k_center(ArrayList<ArrayList<Double>> points, int k){

		int n = points.size();
		ArrayList<Integer> cluster_centers = new ArrayList<Integer>();

		HashMap<Integer,Integer> node_to_cluster = new HashMap<Integer,Integer>();
		ArrayList<ArrayList<Integer>> clusters=new ArrayList<ArrayList<Integer>>();

		//Iterate k times to identify k centers
		for(int i=0;i<k;i++){

			if (i==0){
				//choose a random point as the first center
				int c=rand.nextInt(n);
				cluster_centers.add(c);
			}else{
				//Identify the farthest so far
				
				//Iterate over the clusters which have been already identified
				double max_radius=-1;
				int farthest_point=-1;
				for (int j=0;j<cluster_centers.size();j++){

					//Farthest stores the farthest point in jth cluster
					int farthest=identify_farthest(clusters.get(j),cluster_centers.get(j),points);
					double dist= (get_distance(points.get(farthest),points.get(cluster_centers.get(j))));

					//Compare the jth cluster farthest with the farthest ofother clusters to get global farthest
					if (dist>max_radius){
						max_radius=dist;
						farthest_point=farthest;
					}

				}
				cluster_centers.add(farthest_point);
			}

			clusters=assign_points(cluster_centers,points);

			//Assign points to the clusters

		}
		System.out.println("Approximation algorithm Clusters are"+clusters);
		System.out.println("Cluster centers selected by approximation algorithm are "+cluster_centers);



		//Calculate objective

		HashMap<Integer,Integer> node_to_clust_map = new HashMap<Integer,Integer>();
		double max_radius=-1,total_dist=(double)0.0;
		for (int j=0;j<cluster_centers.size();j++){

			//Farthest stores the farthest point in jth cluster
			int farthest=identify_farthest(clusters.get(j),cluster_centers.get(j),points);
			double dist= (get_distance(points.get(farthest),points.get(cluster_centers.get(j))));
			//Compare the jth cluster farthest with the farthest ofother clusters to get global farthest
			if (dist>max_radius){
				max_radius=dist;
			}
			//total_dist+=dist;
			ArrayList<Integer> lst=clusters.get(j);
			for (int v:lst){
				node_to_clust_map.put(v,j);
				total_dist+=get_distance(points.get(cluster_centers.get(j)),points.get(v));
			}
		}
		System.out.println("Average distance for approx algo is "+total_dist*1.0/points.size());

		try{
			FileWriter fileWriter = new FileWriter("approx_label.txt");
	    	PrintWriter printWriter = new PrintWriter(fileWriter);

			for (int i=0;i<points.size();i++){

	    		printWriter.print(node_to_clust_map.get(i)+"\n");
	    	}
	    	printWriter.close();
			
	    }catch(Exception e) {
  			System.out.println("some problem");//  Block of code to handle errors
		}		
	
		try{
			FileWriter fileWriter = new FileWriter("centers.txt");
	    	PrintWriter printWriter = new PrintWriter(fileWriter);

			for (int i=0;i<cluster_centers.size();i++){

	    		printWriter.print(cluster_centers.get(i)+"\n");
	    	}
	    	printWriter.close();
			
	    }catch(Exception e) {
  			System.out.println("some problem");//  Block of code to handle errors
		}


		return max_radius;
	}
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);

		System.out.println("Please enter the number of points n");
		//n is the number of nodes in the graph
		int n=sc.nextInt();//10;
		int dim=2;

		rand.setSeed(11);

		//list of points. Each list is the set of corrdinates for that point
		ArrayList<ArrayList<Double>> points=new ArrayList<ArrayList<Double>>();
		for (int i=0;i<n;i++){
			ArrayList<Double> p = generate_random_point(2);
			points.add(p);
			System.out.println("point number "+i+" Coordinates:"+p);
		}



		System.out.println("points as a list"+points);







		try{
			FileWriter fileWriter = new FileWriter("point.txt");
	    	PrintWriter printWriter = new PrintWriter(fileWriter);
			for (ArrayList<Double>lst :points){
	    		for (double v: lst){
	    			printWriter.print(v+" ");
	    		}
	    		printWriter.print("\n");
	    	}
	    	printWriter.close();
			
	    }catch(Exception e) {
  			System.out.println("some problem");//  Block of code to handle errors
		}		
		

		








		System.out.println("Please enter the number of clusters wanted, denoted by k");
		int k=sc.nextInt();//3;

		System.out.println("\n\nRunning approximation algorithm");
		double objective = run_k_center(points, k);//
		System.out.println("Max objective for approximation algo is "+objective);


		System.out.println("Running optimal algorithm");
		double optimal_obj=run_optimal_k_center( n,  points,  k);
		

		System.out.println("Optimal objective is "+optimal_obj);




		
		//System.out.println (find_distance(8,aList,arr));
	}
		
}


//objective is 0.77002114
//objective is 0.21357805
//objective is 0.094703674
//objective is 0.04372311
//objective is 0.017799005


//objective is 0.15562628
//objective is 0.66974354
//objective is 0.07801833
//objective is 0.028213568
//objective is 0.008912449

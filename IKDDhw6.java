// Class for computing and representing k-means clustering of expression data.

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class IKDDhw6
{
  // Data members
  private Gene[] genes; // Array of all genes in dataset
  private Cluster[] clusters; //Array of all clusters; null until
                              // performClustering is called.
  
  // Constructor; loads expression data from file <fileName>. The genes array is
  // filled with the genes from the dataset. The clusters array is not filled;
  // to fill it, call performClustering.
  public IKDDhw6(String fileName) 
  {
    clusters = null;
    BufferedReader reader;
    int numGenes;
    String[] splitLine;
    int[] exprValues;
    
    // Creates a new KMeans object by reading in all of the genes
    // and expression levels located in filename
    try 
    {
      reader = new BufferedReader(new FileReader(fileName));
      
      // Count the number of lines to determine how many genes are present
      for(numGenes = 0; reader.readLine( ) != null; numGenes++);
      
      // Close and then re-open the file now that we know its length
      reader.close();
      reader = new BufferedReader(new FileReader(fileName));
      
      // Allocate space for the genes array
      genes = new Gene[numGenes];
      exprValues = null;
      
      // Now, read in each line and create the corresponding Gene object
      for(int i = 0; i < genes.length; i++) 
      {
        String line = reader.readLine();
        // The files are ,-delimited, so split on tabs (,)
        splitLine = line.split( "," );
        // The first entry in the parts array is the gene name, the rest
        // are expression levels for that gene
        exprValues = new int[splitLine.length - 1];
        for(int j = 0; j < exprValues.length; j++) {
        	if(splitLine[j + 1].equals("y")) {
        		exprValues[j] = 1;
        	}else if(splitLine[j + 1].equals("n")) {
        		exprValues[j] = -1;
        	}else {
        		exprValues[j] = 0;
        	}
        }
          
        
        // Finally, create the Gene in the array
        genes[i] = new Gene((i+1)+"", exprValues); 
        
      }
      
      // Lastly, close the file
      reader.close(); 
    }
    catch(FileNotFoundException e) 
    {
      System.out.println( "ERROR:  Unable to locate " + fileName + "." );
      System.exit( 0 ); 
    }
    catch(IOException e) 
    {
      System.out.println( "ERROR:  Unable to read from " + fileName + "." );
      System.exit( 0 );
    } 
  }

  // gets the array of all genes in the dataset.
  public Gene[] getGenes() 
  {
    return genes; 
  }
  
  // Gets the array of all clusters after performing k-means clustering. Note
  // that this method will return null if performClustering has not yet been
  // called.
  public Cluster[] getClusters() 
  {
    return clusters; 
  }
  
  // Perform k-means clustering with the specified number of clusters and
  // distance metric. The "metric" parameter can take two values: "euclid" for
  // Euclidean distance, or "spearman" for Spearman distance.
  public void performClustering(int numClusters, String metric) 
  {	
	clusters = new Cluster[numClusters];
	int alldistnum = 0;
	NodeDist Cur_dist;
	clusters[0] = new Cluster();
	clusters[1] = new Cluster();
	ArrayList<NodeDist> AllDist = new ArrayList<NodeDist>();
    if (!metric.equals("Jaccard_distance"))
      throw new IllegalArgumentException("Parameter <metric> is " +
        metric + ", should be \"Jaccard_distance\".");
    // TODO
    // Add code here to actually perform the clustering algorithm
    clusters[0].getMembers().add(genes[0]);
    for(int i = 0; i < genes.length; ++i){
    	for(int j = i + 1; j < genes.length; ++j) {
    		Cur_dist = new NodeDist(genes[i].getID(),genes[j].getID(),Jacdist(genes[i],genes[j]));
    		AllDist.add(Cur_dist);
    		alldistnum++;
    		if(alldistnum<434) {
    			if(Cur_dist.getDist() < 0.4) {
    				System.out.print("R"+" ");
    				clusters[0].getMembers().add(genes[j]);
    			}else {
    				System.out.print("D"+" ");
    				clusters[1].getMembers().add(genes[j]);
    			}
    				
    			System.out.println(Cur_dist.getNode_begin()+" "+Cur_dist.getNode_end()+" "+Cur_dist.getDist());
    		}
    		
    	}
    }
    System.out.println(alldistnum);
    
    
    //Jacdist(genes[0],genes[1]);
    
  }
  
  public double Jacdist(Gene genes1, Gene genes2) 
  {
	double p = 0.0;
	double q = 0.0;
	double dist = 0.0;
    for(int i = 0; i < genes1.getAttributes().length; ++i){
    	if((genes1.getAttributes()[i] != 0) && (genes2.getAttributes()[i] == 0)) {
    		p = p + 1;
    		q = q + 1;
    	}else if((genes1.getAttributes()[i] == 0) && (genes2.getAttributes()[i] != 0)) {
    		p = p + 1;
    		q = q + 1;
    	}else if((genes1.getAttributes()[i] != 0) && (genes2.getAttributes()[i] != 0)) {
    		if(genes1.getAttributes()[i] != genes2.getAttributes()[i]) {
    			p = p + 1;
    		}
    		q = q + 1;
    	}else if((genes1.getAttributes()[i] == 0) && (genes2.getAttributes()[i] == 0)) {
    	}
    	
    	
    }
    dist = p/q;
    return dist;
    
  }
  
  
  public void WriteToFile() throws IOException {
	  for(int i = 0; i < clusters.length; ++i) {
		  FileWriter fw = new FileWriter("cluster"+(i+1)+".csv");
		  
		  Collections.sort(clusters[i].getMembers(), new Comparator<Gene>(){
				@Override
				public int compare(Gene gene1, Gene gene2) {
					// TODO Auto-generated method stub
					Integer a = Integer.parseInt(gene1.getID());
					Integer b = Integer.parseInt(gene2.getID());
					return a.compareTo(b);
				}   
	        	});
		  
		  for(int j = 0; j < clusters[i].length(); ++j) {
			  fw.write(clusters[i].getMembers().get(j).getID() + "\n");
		  }
		  fw.flush();
		  fw.close();
	  }
	  
  }
  // Main method. Run this program using the following command.
  // java KMeans <input_data_filename> <K> <metric> <output_filename>
  //
  // This program will print out the genes in each cluster, and will also create
  // a jpeg file showing the expression data of each cluster. The jpeg files
  // will be named "<output_filename><clusterNumber>.jpg".
  public static void main( String[] astrArgs ) throws IOException 
  {
    // TODO
    // Add code here to make a new KMeans object (which will load the data).
    // Then perform the clustering and output the results to the screen
    // and create the image files
      
    /*
     * The code commented out here is just an example of how to use
     * the provided functions and constructors.
     * 
     KMeans KM = new KMeans( "yeast_stress.pcl" );
     Cluster C = new Cluster( );
     for( int i = 0; i < KM.getGenes( ).length; ++i )
     C.addGene( KM.getGenes( )[i] );
     C.createJPG( "IKDDhw6", 1 );
     */
	  IKDDhw6 KM = new IKDDhw6( astrArgs[0] ); 
	  KM.performClustering(2, "Jaccard_distance");
	  KM.WriteToFile();
	  
	  
	  
	  
	  
	  
	  /*
	  for( int i = 0; i < KM.getGenes().length; ++i ) {
		  System.out.print(KM.getGenes()[i].getString() + " ");
		  for(int j = 0; j < KM.getGenes()[i].getAttributes().length; ++j) {
			  
			  System.out.print(KM.getGenes()[i].getAttributes()[j] + " ");
		  }
		  System.out.println();
	  }
	  */
	  
  }
}

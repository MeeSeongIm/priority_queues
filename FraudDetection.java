/*  find largest M items from a collection of N items. 
Applications: fraud detection, looking for a certain transaction, maintaining files. */

 
MinPQ<Trans> pq = new MinPQ<Trans>();  // have the ability to delete the min. 
while (StdIn.hasNextLine())            // standard input 
{
 String line = StdIn.readLine();
 Trans item = new Trans(line);        // insert new transaction in the priority queue 
 pq.insert(item);
 if (pq.size() > M)                   // if we have more than M items 
  pq.delMin();                       // throw away the smallest item so that largest M items are being tracked 
} 










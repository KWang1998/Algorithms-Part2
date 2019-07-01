/* *****************************************************************************
 *  Name: K
 *  Date:
 *  Description: Algorithms Part2 Week1
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class WordNet {
    private final Digraph wnDigraph;
    private HashMap<Integer, String> id_synset;
    private HashMap<String, Bag<Integer>> synset_id;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();
        int root = 0;
        id_synset = new HashMap<>();
        synset_id = new HashMap<>();
        In Synsets = new In(synsets);
        In Hypernyms = new In(hypernyms);
        while (Synsets.hasNextLine()) {
            String[] line = Synsets.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            id_synset.put(id, line[1]);
            for (String s : line[1].split(" ")) {
                if (!synset_id.containsKey(s)) {
                    synset_id.put(s, new Bag<>());
                }
                synset_id.get(s).add(id);
            }
        }

        wnDigraph = new Digraph(id_synset.size());
        while (Hypernyms.hasNextLine()) {
            String[] line2 = Hypernyms.readLine().split(",");
            for (int j = 1; j < line2.length; j++) {
                wnDigraph.addEdge(Integer.parseInt(line2[0]), Integer.parseInt(line2[j]));
            }
        }

        DirectedCycle check = new DirectedCycle(wnDigraph);
        if (check.hasCycle()) throw new IllegalArgumentException();
        for (int k = 0; k < wnDigraph.V(); k++) {
            if (!wnDigraph.adj(k).iterator().hasNext()) root++;
        }
        if (root != 1) throw new IllegalArgumentException();
        sap = new SAP(wnDigraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synset_id.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return synset_id.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        Bag<Integer> bagA = synset_id.get(nounA);
        Bag<Integer> bagB = synset_id.get(nounB);
        return sap.length(bagA, bagB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        Bag<Integer> bagA = synset_id.get(nounA);
        Bag<Integer> bagB = synset_id.get(nounB);
        int ancestor = sap.ancestor(bagA, bagB);
        return id_synset.get(ancestor);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}

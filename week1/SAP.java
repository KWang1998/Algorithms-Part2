/* *****************************************************************************
 *  Name: K
 *  Date:
 *  Description: Algorithms Part2 Week1
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private final Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        this.g = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v > g.V() - 1 || w > g.V() - 1)
            throw new IllegalArgumentException();
        int ancestor = ancestor(v, w);
        if (ancestor == -1) return -1;
        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(g, w);
        return bfdpV.distTo(ancestor) + bfdpW.distTo(ancestor);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v > g.V() - 1 || w > g.V() - 1) throw new IllegalArgumentException();
        int result = -1;
        int length = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(g, w);
        for (int i = 0; i < g.V(); i++) {
            if (bfdpV.hasPathTo(i) && bfdpW.hasPathTo(i)) {
                int temp = bfdpV.distTo(i) + bfdpW.distTo(i);
                if (temp < length) {
                    length = temp;
                    result = i;
                }
            }
        }
        return result;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        for (Integer v1 : v) {
            if (v1 == null || v1 < 0 || v1 > g.V() - 1) throw new IllegalArgumentException();
        }
        for (Integer w1 : w) {
            if (w1 == null || w1 < 0 || w1 > g.V() - 1) throw new IllegalArgumentException();
        }
        int ancestor = ancestor(v, w);
        if (ancestor == -1) return -1;
        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(g, w);
        return bfdpV.distTo(ancestor) + bfdpW.distTo(ancestor);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        for (Integer v1 : v) {
            if (v1 == null || v1 < 0 || v1 > g.V() - 1) throw new IllegalArgumentException();
        }
        for (Integer w1 : w) {
            if (w1 == null || w1 < 0 || w1 > g.V() - 1) throw new IllegalArgumentException();
        }
        int result = -1;
        int length = Integer.MAX_VALUE;
        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(g, w);
        for (int i = 0; i < g.V(); i++) {
            if (bfdpV.hasPathTo(i) && bfdpW.hasPathTo(i)) {
                int temp = bfdpV.distTo(i) + bfdpW.distTo(i);
                if (temp < length) {
                    length = temp;
                    result = i;
                }
            }
        }
        return result;
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}

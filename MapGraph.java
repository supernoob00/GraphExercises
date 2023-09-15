/******************************************************************************
 *  Compilation:  javac MapGraph.java
 *  Execution:    java Graph < input.txt
 *  Dependencies: ST.java SET.java In.java StdOut.java
 *  Data files:   https://introcs.cs.princeton.edu/java/45graph/tinyGraph.txt
 *
 *  Undirected graph data type implemented using a symbol table
 *  whose keys are vertices (String) and whose values are sets
 *  of neighbors (SET of Strings).
 *
 *  Remarks
 *  -------
 *   - Parallel edges are not allowed
 *   - Self-loop are allowed
 *   - Adjacency lists store many different copies of the same
 *     String. You can use less memory by interning the strings.
 *
 *  % java Graph < tinyGraph.txt
 *  A: B C G H
 *  B: A C H
 *  C: A B G
 *  G: A C
 *  H: A B
 *
 *  A: B C G H
 *  B: A C H
 *  C: A B G
 *  G: A C
 *  H: A B
 *
 ******************************************************************************/

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *  The {@code Graph} class represents an undirected graph of vertices
 *  with string names.
 *  It supports the following operations: add an edge, add a vertex,
 *  get all the vertices, iterate over all the neighbors adjacent
 *  to a vertex, is there a vertex, is there an edge between two vertices.
 *  Self-loops are permitted; parallel edges are discarded.
 *  <p>
 *  For additional documentation,
 *  see <a href="https://introcs.cs.princeton.edu/45graph">Section 4.5</a> of
 *  <i>Computer Science: An Interdisciplinary Approach</i>
 *  by Robert Sedgewick and Kevin Wayne.
 */
public class MapGraph implements Graph {

    // symbol table: key = string vertex, value = set of neighboring vertices
    private Map<String, Set<String>> st;
    // current number of edges in the graph
    private int edges = 0;

    /**
     * Initializes an empty graph with no vertices or edges.
     */
    public MapGraph() {
        st = new HashMap<>();
    }

    /**
     * Initializes a graph from the specified file, using the specified delimiter.
     *
     * @param filename the name of the file
     * @param delimiter the delimiter
     */
    public MapGraph(String filename, String delimiter) throws IOException {
        st = new HashMap<>();
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String line = null;
        while ((line = in.readLine()) != null) {
            String[] names = line.split(delimiter);
            for (int i = 1; i < names.length; i++) {
                addEdge(names[0], names[i]);
            }
        }
    }

    public MapGraph(Graph g) {
        st = new HashMap<>();
        for (String v : g.vertices()) {
            for (String w : adjacentTo(v)) {
                addEdge(v, w);
            }
        }
    }

    public boolean hasEdge(String v, String w) {
        return st.get(v).contains(w);
    }

    public boolean hasVertex(String v) {
        return st.containsKey(v);
    }

    /**
     * Adds the edge v-w to this graph (if it is not already an edge).
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     */
    public void addEdge(String v, String w) {
        if (v.equals(w)) {
            throw new IllegalArgumentException("Vertex arguments cannot be the same.");
        }
        if (!st.containsKey(v)) st.put(v, new HashSet<>());
        if (!st.containsKey(w)) st.put(w, new HashSet<>());

        if (!hasEdge(v, w)) {
            st.get(v).add(w);
            st.get(w).add(v);
            edges++;
        }
    }

    /**
     * Returns the vertices in this graph.
     *
     * @return the set of vertices in this graph
     */
    public Iterable<String> vertices() {
        return st.keySet();
    }

    public Iterable<String> adjacentTo(String v) {
        return st.get(v);
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return number of vertices in this graph
     */
    public int vertexCount() {
        return st.size();
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph.
     */
    public int edgeCount() {
        return edges;
    }

    public int degree(String v) {
        Set<String> connections = st.get(v);
        if (connections == null) {
            return 0;
        }
        return connections.size();
    }

    /**
     * Returns a string representation of this graph.
     *
     * @return string representation of this graph
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String v : st.keySet()) {
            s.append(v + ": ");
            for (String w : st.get(v)) {
                s.append(w + " ");
            }
            s.append('\n');
        }
        return s.toString();
    }

    private void validateVertex(String v) {
        if (!st.containsKey(v)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Unit tests the {@code Graph} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

    }

}

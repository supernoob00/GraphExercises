public interface Graph {
    boolean hasEdge(String v, String w);

    boolean hasVertex(String v);

    void addEdge(String v, String w);

    Iterable<String> vertices();

    Iterable<String> adjacentTo(String v);

    int vertexCount();

    int edgeCount();

    int degree(String v);
}

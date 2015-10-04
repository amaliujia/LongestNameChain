How to run the code and tests?
-------------------------------------------------------------------
(Make sure run the commands below in Java 1.7 enviroment).
1. Compile all the code:
    javac  *.java
2. Run unit tests:
    java GraphTest
4. Run Functional tests: 
    java FunctionTest testOne.txt testOneOutput.txt testTwo.txt testTwoOutput.txt 
    (note: names.txt and names2.txt is input file, testOne.txt and testTwo.txt is corrsponding output file) 


Ideas, design and running time.
-------------------------------------------------------------------
1. Assume the input contains a list of names. So basically each name
   can be represented as a vertex in graph, and there is a directed edge
   from vertex v to w if v and w can form a name chain.

2. If input data can be modeled as a graph, then any corner case should be 
    considered? I think graph should not contain any cycles. Cycle means I
    can form an infinit long name chain. So after I build the graph on the 
    input data, first thing I need to do is detecting cycle. If cycle exists,
    return Infinity.

3. Then, what if graph doesn't contain any cycle? OK, I need to find the
   topological order of graph. After that, based on the order, I first compute
   the length of longest path in graph from each vertex, and then get the longest
   one from those paths. The longest one is actually the longest name chain in
   this graph. So the last step is print the name chain itself. 


Running time: (V is the set of vertices and E is the set of edges.)
-----------------------------------------------------------------------
1. Build the graph based on input data. This step needs O(|E|).
2. Used DFS to detect cycle in directed graph. This step needs O(|V| + |E|).
3. Find the topological order of graph. Needs O(|V| + |E|).
4. Find longest path from each vertex. Needs O(|V| * (|V| + |E|)).
5. Find the longest path for all vertex. Needs O(|V).
6. Find the generate the name chain. O(|V| + |E|).

So total running time is O(|V|^2 + |V||E|).


Space
-----------------------------------------------------------------------
Space should be O(|V| + |E|).



Unit tests
-----------------------------------------------------------------------
I did catch some bugs by units tests.

1.I used wrong vertex to do recursive calls, therefore I got a stackoverflow error.
2. There were some edges missed, so I got incorrect output.
3. There was a bug about printing the final name chain.


Functional tests
----------------------------------------------------------------------- 
I didn't catch bugs during this phase.

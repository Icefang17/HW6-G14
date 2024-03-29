# CS4750/7750 HW #6 (20 points) 
### HW6-G14
 
In this programming assignment, implement the backtracking search with the MRV and degree  heuristic,  together  with  forward  checking,  to  solve  9x9  Sudoku  puzzle (https://en.wikipedia.org/wiki/Sudoku).

Run your program on the following three instances and report your results. Terminate your program if it runs for more than one hour. 

a)
|0|1|2|X|3|4|5|X|6|7|8| 
|-|-|-|-|-|-|-|-|-|-|-|
|0|0|1|-|0|0|2|-|0|0|0|
|0|0|5|-|0|0|6|-|0|3|0|
|4|6|0|-|0|0|5|-|0|0|0|
|-|-|-|-|-|-|-|-|-|-|-|
|0|0|0|-|1|0|4|-|0|0|0|
|6|0|0|-|8|0|0|-|1|4|3|
|0|0|0|-|0|9|0|-|5|0|8|
|-|-|-|-|-|-|-|-|-|-|-|
|8|0|0|-|0|4|9|-|0|5|0|
|1|0|0|-|3|2|0|-|0|0|0|
|0|0|9|-|0|0|0|-|3|0|0|

b)
|0|1|2|X|3|4|5|X|6|7|8| 
|-|-|-|-|-|-|-|-|-|-|-|
|0|0|5|-|0|1|0|-|0|0|0|
|0|0|2|-|0|0|4|-|0|3|0|
|1|0|9|-|0|0|0|-|2|0|6|
|-|-|-|-|-|-|-|-|-|-|-|
|2|0|0|-|0|3|0|-|0|0|0|
|0|4|0|-|0|0|0|-|7|0|0|
|5|0|0|-|0|0|7|-|0|0|1|
|-|-|-|-|-|-|-|-|-|-|-|
|0|0|0|-|6|0|3|-|0|0|0|
|0|6|0|-|1|0|0|-|0|0|0|
|0|0|0|-|0|7|0|-|0|5|0|

c)  
|0|1|2|X|3|4|5|X|6|7|8| 
|-|-|-|-|-|-|-|-|-|-|-|
|6|7|0|-|0|0|0|-|0|0|0|
|0|2|5|-|0|0|0|-|0|0|0|
|0|9|0|-|5|6|0|-|2|0|0|
|-|-|-|-|-|-|-|-|-|-|-|
|3|0|0|-|0|8|0|-|9|0|0|
|0|0|0|-|0|0|0|-|8|0|1|
|0|0|0|-|4|7|0|-|0|0|0|
|-|-|-|-|-|-|-|-|-|-|-|
|0|0|8|-|6|0|0|-|0|9|0|
|0|0|0|-|0|0|0|-|0|1|0|
|1|0|6|-|0|5|0|-|0|7|0|

In your submission, report the following:

1. (6 points) A  description of your CSP problem formulation and implementation of the search method and heuristics. If you use some existing code on the web, give appropriate references and credits. 

2. (10 points) For each of the three instances, report the results of the first 5 steps of your program, including:
<ul>

    a) the variable selected

    b) the domain size of the selected variable

    c) the degree of the selected variable

    d) the value assigned to the selected variable
</ul>

3. (2 points) For each instance, report the solution and CPU execution time in seconds.

4. (2 points) Your code with appropriate comments.

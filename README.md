# elevator
roughly based on https://mesosphere.com/careers/challenges/intern/

## preamble
In this section I will attempt to explain my approach to this challenge.

I began contemplating the challenge without outside stimulus.
This led me down several paths.
The first was that assigning people elevator cars must have somthing in common with shortest paths calculation. As such 
https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm popped to mind, though Djikstra's algorithm is mentioned therein, run
on every vertex as the start vertex for better runtime for sparse graphs.
It immediately occurred to me that for the general problem of optimizing a traversal, the Traveling salesman is at the root.
_But_ I could not immediately figure out how to reduce the problem of several cars and online requests to the traveling salesman.
Also, the shortest paths mentioned above did not immediately map into my problem domain with elegance.

As such, as many have done before me, I googled up the challenge itself and found a treasure trove of information and github repos,
  in one langauge or another attempting to solve the same problem.
  One that stood out was https://github.com/sitano/mesosphere-elevator.
  Another was https://github.com/allquantor/mesosphere-challenge.
  Yet another was _**???**_
  
While I could not exactly reduce my problem to the traveling salesman. Some claimed that the problem is reducible to
https://en.wikipedia.org/wiki/Job_shop_scheduling. Some to [time-dependent traveling salesman](http://www.sciencedirect.com/science/article/pii/S1572528608000339)
What all these links linked to was the realization that: 
There is no best algorithm! _And_ the usage pattern of the elevator would determine the choice of optimization.
(See: http://webcache.googleusercontent.com/search?q=cache:http://www.columbia.edu/~cs2035/courses/ieor4405.S13/p14.pdf).
Heuristics were then proposed such as: https://en.wikipedia.org/wiki/Ant_colony_optimization_algorithms
Or genetic algorithms.

Therefore, I have decided to implement a plugable design where one routing algorithm could be substituted for another.
Also, I will implement just a naive subset of these algorithms. I then wish to explore the asynchronous nature of elevator
communication with the controller and see if the added complexity makes a real difference in implementation.

I gather from the previous challenges that a usual 4 hours is given to the task, and that other coders have also not counted the 
 research time and preamble writing as part of the 4. I will try to log what I had been able to complete in 4 hours. I will also try to
 log the complete amount of time I spent on the project
 
## tasks
- [ ] single elevator
    - [ ] FCFS, First Come First Served
    - [ ] SSF, Shortest Seek First or Nearest Car
    - [ ] SSF but try to avoid starvation
- [ ] multiple elevators
    - [ ] ???
    - [ ] async
    - [ ] akka
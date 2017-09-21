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
  in one language or another attempting to solve the same problem.
  One that stood out was https://github.com/sitano/mesosphere-elevator.
  Another was https://github.com/allquantor/mesosphere-challenge.
  Yet another was https://github.com/adamkennedy/Mesovator
  
While I could not exactly reduce my problem to the traveling salesman. Some claimed that the problem is reducible to
https://en.wikipedia.org/wiki/Job_shop_scheduling. Some to [time-dependent traveling salesman](http://www.sciencedirect.com/science/article/pii/S1572528608000339)
What all these links linked to was the realization that: 
There is no best algorithm! _And_ the usage pattern of the elevator would determine the choice of optimization.
(See: http://webcache.googleusercontent.com/search?q=cache:http://www.columbia.edu/~cs2035/courses/ieor4405.S13/p14.pdf).
Heuristics were then proposed such as: https://en.wikipedia.org/wiki/Ant_colony_optimization_algorithms
Or genetic algorithms. It seems to me that only the destination control systems would be applicable to reduction to the aforementioned
routing/scheduling algorithms. I have unfortunately not managed to get there 

I have implemented just a small naive subset of these algorithms. More specifically the "Elevator Algorithm" for a single elevator
and the "Nearest Car" algorithm for multiple elevators.

I gather from the previous challenges that a usual 4 hours is given to the task, and that other coders have also not counted the 
 research time and preamble writing as part of the 4. I will try to log what I had been able to complete in 4 hours. I will also try to
 log the complete amount of time I spent on the project.

## notes
 - I modify the API suggested in the original challenge to be IMO more functional
   This [blog post](https://underscore.io/blog/posts/2017/06/02/uniting-church-and-state.html) explains that 
>   The Church encoding gives us a way to relate the OO and FP representation.
 - I would like to propose that an elevator has a state and a function on that state produces
 and elevator within the same or a different state. I wonder if a state monad might help or obstruct the design
 - A trivial one: I would like to split the request into direction (up/down) and then to floor. This seems more 
 realistic in the simple scenario - I think I have succeeded doing that.
 - I have put aside volume considerations and thus the amount of people using the elevators have been dropped
 - The amount of work that had gone into this had been in the ballpark of 10 net hours, putting aside the research
 - a full git log shows the transformation of ideas into the present design
                                          

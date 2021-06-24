# Team Members
Jacob Karabin (jxk5861@psu.edu)
Mohammad Alhmoudi

# TestingToolJavaFX
A structural testing tool that provides the user with the ability to draw a program graph and run various structural testing algorithms on it. 
Implemented algorithms include C1, C1P, and DU-paths testing.

This was our submission for our SWENG-421 final project with a focus on implementing the design patterns learned in class. Details on design patterns below the images.

# Tool Images
## Drawing a graph
![image](https://user-images.githubusercontent.com/58671117/123255173-85956500-d4bd-11eb-8cba-ff2e4a3dac73.png)
## Running a test
![image](https://user-images.githubusercontent.com/58671117/123255248-9e9e1600-d4bd-11eb-84d1-7657477b9e50.png)
## Viewing Test Results
![image](https://user-images.githubusercontent.com/58671117/123255387-c7bea680-d4bd-11eb-9780-d83de50fbe3a.png)
## Canceling Lengthy Tests
![image](https://user-images.githubusercontent.com/58671117/123255525-f472be00-d4bd-11eb-8c6e-f8e1142948bb.png)
## Test Results
![image](https://user-images.githubusercontent.com/58671117/123255424-d1480e80-d4bd-11eb-8d94-4174a64839e3.png)
## Loading in Additional Tests
![image](https://user-images.githubusercontent.com/58671117/123255810-40bdfe00-d4be-11eb-867b-7e44be846103.png)
![image](https://user-images.githubusercontent.com/58671117/123255775-38fe5980-d4be-11eb-8081-994ce7d28c35.png)
## Now the DU-paths testing appears
![image](https://user-images.githubusercontent.com/58671117/123255832-46b3df00-d4be-11eb-9fac-ca7a62f3816c.png)

Design Patterns:

### Creational pattern (**Prototype**).
Prototype is chosen for the creational pattern since creating a shallow/deep copy of a graph or a path through the graph is useful for many different applications of testing. One example is when conducting c1p testing. Each path is built starting at a specific vertex, and then each time a branch is reached, the path is cloned, once for each branch, and then c1p testing is conducted for each new path. We also use it for cloning the entire graph before a test is performed. This prevents modifications of the graph during testing from affecting output of the algorithm.

### Partitioning patterns (**Filter**).  
When conducting c1p testing, the program will use an algorithm based on DFS to find all paths. Some of the paths fail if the path cannot reach the end node, while others succeed in reaching it. We will use a filter design pattern to filter out valid and invalid paths. This way the user can choose which types of paths they want and apply different filters, such as an AllPathFilter, ValidPathFilter, InvalidPathFilter and read the c1p path list in different ways. For example, the ValidPathFilter will only show paths which “succeed.” 

The test results object will provide the user with options to filter the data in their desired way, and display the filtered data without changing the original data source. Our design is similar to using the Java Stream’s paths.stream().filter(C1PPath::succeeds) to produce a filtered stream of the list of paths.

### Structural patterns (**Dynamic Linkage**).   
Dynamic Linkage can be used to load arbitrary classes. Using this design pattern allows users to load classes that they have no prior knowledge of. The way it’s implemented in this system is by having some basic path testing methods like C1 or C1P testing methods, and then users can dynamically load any more advanced testing methods like DU path testing which may not have been initially available with the system. We also chose dynamic linkage because it allows testing methods to modify the testing environment. This way, a test can use the TestingEnvironmentIF to access the currently drawn graph to test it. The test can even draw additional things on the graph if it desires (such as showing declares and use nodes in different colors during selection, although no such features is implemented).

### Behavioral patterns (**State**).
When drawing a graph with the GUI, the program will feature multiple states depending on the component being drawn. For example, when drawing a vertex it will be in the vertex state (draws a circle on click). While in the edge state it will require clicking on two vertices to create an edge between them. Using states makes the code much cleaner, since the PrimaryController Mediator object does not need to be very complex. Instead, it forwards its events to its state object which then processes them and returns the new state. 

### Concurrency  patterns (**Future**).
When conducting a test, the test will be conducted on another thread so that the GUI thread does not freeze while the computation is running (if it is very expensive). We will use the future design pattern to allow the GUI thread to get the computation results from the computation thread without waiting for it to complete, and allow the user to select which completed results to display. This will also allow multiple tests to be conducted at the same time, and run on separate threads for better performance. The PrimaryController itself only cares about the Future object and periodically checks it for results to allow the user to display.

### Some additional patterns (**Two-Phase Termination**, **Null Object**)
A variation of Two-Phase Termination is also used for canceling a running test. Occasionally, a test may take too long to run and if it is not stopped it may cause the program to run out of memory and crash the thread. This is not ideal so we provide the user with a way to stop the execution of a test if they think it has gone on for too long. Since running tests are implemented in combination with the Future design pattern, the two phase termination relies on canceling the TestIF itself instead of interrupting the thread.
We also use NullObject for dealing with tests which fail or are canceled. If another programmer develops a new test to load in the future, they may attempt to return a null value if the test fails. This will crash the program and is unfavorable. To account for this, the Future checks if the result is null before it returns it and returns a NullResult instead. The NullResult object will display a failure message when its display method is called.

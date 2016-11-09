# QA_Deliverable4_David
IS2545 - DELIVERABLE 4: Performance Testing

1. Summary
This assignment is for Performance Testing against a given program called Conway's Game of Life simulation. The rationale is to use profiling tool, VisualVM, to determin which method in the program is most CPU intensive, then adding pinning test and refactor the method to be more performant.

2. Methods found to be refactored via VisualVM
The methods I found that are CPU-intensive are as follows,

  2.1 MainPanel.convertToInt() 
![picture1](https://cloud.githubusercontent.com/assets/16587395/20127824/e7699fa2-a610-11e6-8594-6d565e22064e.png)

  2.2 MainPanle.runContinuous()
![picture2](https://cloud.githubusercontent.com/assets/16587395/20127860/41b3453a-a611-11e6-8b64-bc307aad20c9.png)

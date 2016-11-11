# QA_Deliverable4_David

IS2545 - DELIVERABLE 4: Performance Testing

     1. Summary

 This assignment is for Performance Testing against a given program called Conway's Game of Life simulation. The rationale is to use profiling tool, VisualVM, to determin which method in the program is most CPU intensive, then adding pinning test and refactor the method to be more performant.

     2. Methods found to be refactored via VisualVM

 The methods I found that are CPU-intensive are as follows,

  2.1 MainPanel.convertToInt() 
 ![picture1](https://cloud.githubusercontent.com/assets/16587395/20127824/e7699fa2-a610-11e6-8594-6d565e22064e.png)

 From the image, we can see the MainPanel.convertToInt() is using CPU heavily, covering 50% of total time, thus should be refacored.

  2.2 MainPanel.runContinuous()
 ![picture2](https://cloud.githubusercontent.com/assets/16587395/20127860/41b3453a-a611-11e6-8b64-bc307aad20c9.png)

 The MainPanel.runContinuous() method also using CPU intensivly, covering 38% of total CPU usage time, therefore need to be refactored.

  2.3 Cell.toString()
 ![picture3](https://cloud.githubusercontent.com/assets/16587395/20127912/c2a477ea-a611-11e6-91ec-e909cb103094.png)

 From the image, the Cell.< init >() is little bit CPU-intensive, and after going back to the source code, found this method is necessary to be refactored.
  
        3. Refactoring of methods 

 3.1 MainPanel.converToInt()

 Original code: 
 
			 private int convertToInt(int x) {
				   int c = 0;
				   String padding = "0";
				   while (c < _r) {
				       String l = new String("0");
				       padding += l;
				       c++;
				   }
				   String n = padding + String.valueOf(x);
				   int q = Integer.parseInt(n);
				   return q;
			       }
    
 Code after refactoring:

			private int convertToInt(int x) {
				if (x < 0) throw new NumberFormatException();
				return x;
			    }
    
 The reason for refactoring as above is because the padding in the intial method is total unnecessary, and the while loop causes the CPU-intensive.

 The profiling after refactoring as below:

 ![picture1](https://cloud.githubusercontent.com/assets/16587395/20128223/a7dc7a04-a614-11e6-8e4b-4f83cf21a5b9.png)

 3.2 MainPanel.runContinuous()

 Original code:

				 public void runContinuous() {
					_running = true;
					while (_running) {
					    System.out.println("Running...");
					    int origR = _r;
					    try {
						Thread.sleep(20);
					    } catch (InterruptedException iex) {
					    }
					    for (int j = 0; j < _maxCount; j++) {
						_r += (j % _size) % _maxCount;
						_r += _maxCount;
					    }
					    _r = origR;
					    backup();
					    calculateNextIteration();
					}
				    }
    
 Code after refactoring:

				 public void runContinuous() {
					_running = true;
					while (_running) {
					    System.out.println("Running...");
					    backup();
					    calculateNextIteration();
					}
				    }

 The reason for refactoring as above is because the for loop in the inital method is useless, and the thread.sleep(20) causes the CPU-intensive as well.

 The profiling after refacotring as below:

 ![picture1](https://cloud.githubusercontent.com/assets/16587395/20128337/7c95e564-a615-11e6-8654-eaaaa988c9c5.png)

 3.3 Cell.toString()

 Original code:

				 public String toString() {
					String toReturn = new String("");
					String currentState = getText();     
					for (int j = 0; j < _maxSize; j++) {
					    toReturn += currentState;    
					}
					if (toReturn.substring(0,1).equals("X")) {
					    return toReturn.substring(0,1);
					} else {
					    return ".";
					}
				}

 Code after refactoring:

				public String toString() {
					String toReturn = new String("");
					String currentState = getText();     
				  toReturn += currentState;    
					if (toReturn.substring(0,1).equals("X")) {
					    return toReturn.substring(0,1);
					} else {
					    return ".";
					}
				}

 The reason for above refactoring is because the for loop in the inital method is unnessary and causes CPU-intensive.

 The profiling after refactoring as below,

 ![picture1](https://cloud.githubusercontent.com/assets/16587395/20128450/1af16a30-a616-11e6-93bc-cd90aecdee49.png)

     4. Pinning test for each refactoring

 The pinning test code contains in the Test Packages of this repo, and it is compatible for both original code and refactored code.
 
 When doing the pinning testing, I found that to do testing to private and void method is very difficult, but difficulty does not mean impossible. After trying bunch of times by myself without success, I found the following links are useful and they are the references for my pinning testing:
 
 http://saturnboy.com/2010/11/testing-private-methods-in-java/
 
 https://github.com/rickykoter/CS1632_Deliverable5/blob/master/PinningTests.java
 
 From the above reference, I learned how to do unit testing against private method using 
           method.setAccessible(true);
 And void method using reflection.

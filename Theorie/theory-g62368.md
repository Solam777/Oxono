D'accord ! Voici la traduction en gardant la structure avec les hashtags :

---

# OO Reminder and UML #

# Q1 #

1. It will first display (0,0), then (2,2) because of p.move.
 
2. It could not be used or called in.
 
3. Yes, we will get a compilation error.

# Q2 #

1. We get a compilation error of the type: java: x has private access in org.example.Point because we cannot directly access the value x.
 
2. Since we are overloading the method, Java will choose the appropriate method, so we will have in the console: method move(int, int) (2.0, 2.0).
 
3. There will be a compilation error because we are redefining a method that already exists without changing its signature, in this case, its parameters.

# Q3 #

1. A compilation error because `this` must be the first instruction.
2. Constructor Point in class Point cannot be applied to given types.
3. There are no more errors because Java creates a default constructor, sets x and y to 0, and the program displays a new point (2,2).

# Q4 #

1. [(0.0),5] the first point created, [(2.5),5] the point moved to 2.5, and finally [(2.5),10] the radius multiplied by 2.
 
2. Only point p.

# Q5 #

1. [(0.0),5] for point p, [(2.5),5] the point moved to 2.5, and finally [(0.0),5], which is point p2.
2. There is only one instance of the class Point and one instance of the class Circle; points p and p2 are instantiated by the class Point, and the center attribute of the circle is referenced by Point.
3. 
   [(0.0),5], [(0.0),5], [(-2.5),5]
4. 
   [(0.0),5], [(0.0),5], [(0.0),5]
5. 
   The class Point has 3 instances.
   The Circle has only one instance.
   Points p and p2 are instantiated by the class Point.
   The point c of the circle references point p.

# Q6 #

1.  
   (3.0, 6.0) - FF0000FF  
   x: 3.0  
   color: FF0000FF
2. 
   java: cannot find symbol  
   symbol: method getColor()  
   location: variable p of type org.example.Point  
   The last line poses a problem because p is just a Point and not a ColoredPoint. The method used is for colored points, and if we remove this line, we no longer have any errors.
3. 
   No, because it is not an instance of ColoredPoint.

4. 
   No.
5.  
   java: cyclic inheritance involving org.example.Point because ColoredPoint is already a subclass of the class Point.
6. 
   java: cannot inherit from final org.example.Point because a final class cannot have children.

# Q7 #

1. 
   Yes, because all classes derive from the Object class.
2. 
   Yes, because all classes derive from the Object class.
3. 
   Yes, we can add it, and it is defined in the Object class, which is a superclass inherited by all created classes.
   We can call it because ColoredPoint also inherits from the Object class.

# Q8 #

1. 
   java: <identifier> expected.
2. 
   java: constructor Point in class org.example.Point cannot be applied to given types; it serves to call the constructor of the parent class in the inheriting class.
3. 
   No, there are no more errors.

# Q9 #

1. 
   Constructor of A  
   Constructor of B  
   Constructor of C

2. 
   java: cannot find symbol.
 
# Q10 #

1. 
   (0.0, 0.0) - not pinned  
   (1.0, 1.0) - pinned
2. 
   The one from the class PinnablePoint.
3. 
   java: unreported exception java.lang.Exception; must be caught or declared to be thrown.
4. 
   No, thanks to IllegalStateException.

5. 
   No.

6. 
   No.

7. 
   No, there would be no error.
8. 
   There is no error.
9. 
   It calls the parent method, meaning the one from the Point class.

# Q11 #

1. 
   There is an error.

2.     
   No more errors.

# Regex #

# Q1 #
1. ^g\d{5}$
2. ^(g\d{5})(\s+g\d{5})*$  
3. ^bonjour.merci$
4. ^add\s+circle\d{3}\w(a-z)$
5. ^move\s+\d{1}\s+-?\d$

# Q2 #
   It contains the entire string.


# Lambda #

# Q1 # 
 1. filter(myList, p -> p.getFirstname().startsWith("J"))
 2. filter(myList,p -> p.getFirstname().startsWith("J") && p.getAge() < 50)

# Q2 # 
 1. (word1, word2) -> word1.length() - word2.length()
 2. (word1, word2) -> word1.charAt(0) - word2.charAt(0)

# Q3 # 
 1. 3, 8, and 9


 # javafx-Base #

 # Q1 #
  1. v1 is the width and v2 is the height
  2. A window with a transparent background.
  3. setTop(helloText): This places the helloText at the top of the BorderPane, 
     setBottom(helloText): This positions the helloText at the bottom of the BorderPane, 
     setLeft(helloText): This places the helloText on the left side of the BorderPane, 
     setRight(helloText): This positions the helloText on the right side of the BorderPane,

 # Q2 #
  1.  checkBox1 enabling/disabling components, checkBox2 acting as a visual indicator,
      and checkBox3 having distinct initial states or labels, all influenced by their respective event handlers.

   2. checkBox1 may not be centered and could be placed according to the default alignment rules (top-left for instance).
      checkBox3 will also lose its center alignment and may appear in the default position dictated by the layout manager.












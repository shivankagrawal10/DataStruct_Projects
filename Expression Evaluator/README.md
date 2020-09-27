# Expression Evaluation #
In this assignment you will implement a program to evaluate an arithmetic expression

Worth 80 points (8% of course grade)

## Expressions ##
Here are some sample expressions of the kind your program will evaluate:
```
3
Xyz
3-4*5
a-(b+A[B[2]])*d+3
A[2*(a+b)]
(varx + vary*varz[(vara+varb[(a+b)*33])])/55
```
The expressions will be restricted to the following components:
```
Integer constants
Simple (non-array) variables with integer values
Arrays of integers, indexed with a constant or a subexpression
Addition, subtraction, multiplication, and division operators, i.e. '+','-','*','/'
Parenthesized subexpressions
```
Note the following:
```
Subexpressions (including indexes into arrays between '[' and ']') may be nested to any level
Multiplication and division have higher precedence than addition and subtraction
Variable names (either simple variables or arrays) will be made up of one or more letters ONLY (nothing but letters a-z and A-Z), are case sensitive (Xyz is different from xyz) and will be unique.
Integer constants may have multiple digits
There may any number of spaces or tabs between any pair of tokens in the expression. Tokens are variable names, constants, parentheses, square brackets, and operators.
Implementation and Grading
You will see a project called Expression Evaluation with the following classes in package app:
```
## Variable ##

This class represents a simple variable with a single value. Your implementation will create one Variable object for every simple variable in the expression (even if there are multiple occurrences of the same variable).

You don't have to implement anything in this class, so do not make any changes to it.

## Array ##

This class represents an array of integer values. Your implementation will create one Array object for every array in the expression (even if there are multiple occurrences of the same array).

You don't have to implement anything in this class, so do not make any changes to it.

## Expression ##

This class consists of methods for various steps of the evaluation process:

### 20 pts: makeVariableLists ###
- This method populates the vars and arrays lists with Variable and Array objects, respectively, for the simple variable and arrays that appear in the expression.

You will fill in the implementation of this method. Make sure to read the comments above the method header to get more details.

loadVariableValues - This method reads values for all simple variables and arrays arrays from a file, into the Variable and Array objects stored in the vars and arrays array lists. This method is already implemented, do not make any changes.

### 60 pts: evaluate ###
- This method evaluates the expression.

You will fill in the implementation of this method.

## Evaluator ##
The application driver, which calls methods in Expression. You may use this to test your implementation. There are two sample test files etest1.txt and etest2.txt, appearing directly under the project folder.

You are also given the following class in package structures:

Stack, to be (optionally) used in the evaluation process
Do not add any other classes. In particular, if you wish to use stacks in your evaluation implementation do NOT use your own stack class, ONLY use the one you are given. The reason is, we will be using this same Stack class when we test your implementation.

Rules of implementation
You may NOT modify any of the files except Expression.java in ANY way.
You may NOT make ANY modifications to Expression.java EXCEPT:
Write in the bodies of the methods you are asked to implement,
Add private helper methods as needed (including the recursive evaluate method discussed below.)
Note that the java.io.*, java.util.*, and java.util.regex.* import statements at the top of the file allow for using ANY class in java.io, java.util, and java.util.regex without additional specification or qualification.

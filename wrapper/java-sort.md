CS566 Homework-3
Due – October 10, 6:00 PM, at the class beginning
 
All work must be your own.  Upload copy of your homework and any supporting materials (source code and test cases) online to Assignment3 in the CS566_A2 course site
No extensions or late submissions for anything other than major emergency. 
 

(1) [25 pts.]  To sort or not to sort. Outline a reasonable method of solving each of following problems. Give the order of the worst-case complexity of your methods.

a.  You are given a pile of thousands of telephone bills and thousands of checks sent in to pay the bills. Assume telephone numbers are on the checks. Find out who didn’t pay.

b. You are given an array in which entry contains the title, author, call number, and publisher of all books in a school library and another array of 30 publishers. Find out how many of the books where published by each of those 30 companies.

c.  You are given an array containing checkout records of all books checked out of the campus library during the past year. Determine how many distinct people check out at least one book.

(2) [25]  A sorting method is stable if for any i<j such that initially E[i] = E[j], the sort moves E[i] to E[k] and moves E[j] to E[m] for some k and m such that k < m.  Which of the following algorithms are stable? For each that is not, give an example in which relative order of two equal keys  is changed.

a. Insertion Sort       stable   
b. Maxsort          stable   
c. QuickSort     unstable   
d. Heapsort      unstable    
e  MergeSort    stable   
f. Radix Sort    stable   
stable: a、b、e、f

unstable :    

```
sort      |   source Array  | dest Array  
QuickSort |    	            |   
Heapsort  |    	            |    
```

(3) [25 pts.] Given the following file:

26,5,77,1,61,11,59,15,48,19  

Sort this file by hand using Heapsort and answer the following questions:
(a)    Draw the initial COMPLETE tree representation of the file BEFORE the HEAP is created.
(b)   Draw the initial heap.
(c)    Continue with the sort for two iterations PAST the initial heap – i.e., you pop off 77 and 61 from the root. NOW RESTRUCTURE THE HEAP AFTER 61 is popped – what is the NEW ROOT?



(4) [25 pts.] Use Quicksort as shown in class and sort the following file by hand and selecting the first element as the split element:

72,20,73,42,11,80,39,30,100,46,88,32,21

(a)    After the initial swapping, there are two subfiles – one to the left of 72 and one to the right of 72. What are they in order?

(b)   Continue sorting the entire file until totally sorted. During the entire sort – what was the maximum number of inversions removed by a single comparison and swap? 






# Parallel Euler Number

It is a program that calculates Euler Number fast, with high precision.Calculation of 'e' is done by approximation to sum of infinite series.

![image](https://user-images.githubusercontent.com/79646050/128230355-7f6e8e0d-ce24-4b0a-8590-c8af149472e1.png)

In order to do the calculaton the user should give an upper bound of the sum as a parameter. The sum is divided to partial sums.
The number of partial sums depends on the number of threads and granularity that the user gives as parameters.
Every partial sum is calculated simultaneously by different threads in order to find the approximation of 'e' fast,
otherwise the calculation of factorials will be a slow operation.  

    Input parameters:
    1. upper bound for the sum
    2. number of threads
    3. granularity
    4. precision of the approximation
    
    Output:
    1. Approximation of 'e'
    2. Time for the whole calculation 

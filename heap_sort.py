
# python 3.4.1
# heap queue algorithm on binary trees.
# sorting from smallest to largest element.


import heapq 

def heapsort(t):
    h = []
    for i in t:
        heapq.heappush(h,i)
    return [heapq.heappop(h) for i in range(len(h))]


print(heapsort([11,3,5,7,90,2,4,6,1,0]))







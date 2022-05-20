import sys
print ('Number of Arguments:', len(sys.argv), 'arguments.')
print ('Argument List:', str(sys.argv))
print('This is Python Code')
print('Executing Python')
print('From Java')
for i in range(10000000):
    x = i ** 2
f = open("out.txt", "w")
f.write("argc: " + str(len(sys.argv)) + "\n")
f.write("argv: " + str(sys.argv) + "\n")
f.close()

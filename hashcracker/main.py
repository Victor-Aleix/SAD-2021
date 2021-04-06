import hashlib
import threading
import timeit
import time
import argparse


# time starts counting
start = timeit.timeit()

# parsing arguments from terminal input
parser = argparse.ArgumentParser()
parser.add_argument('-w', '--wordlist')  # wordlist
parser.add_argument('-f', '--hashfile')  # hash
parser.add_argument('-m', '--mode')  # hashing algorith,
args = parser.parse_args()
with open(args.wordlist) as f:
    content = f.read().splitlines()
with open(args.hashfile) as f:
    hash = f.readline().splitlines()

# defining functions for encoding strings

def md5():
    print("Trying to crack the hash " + hash[0] + "...")
    for i in range(0, 30):
        result = hashlib.md5(content[i].encode())
        if(result.hexdigest() == hash[0]):
            print("Hash cracked, the decoded string is : " + content[i])


def sha1():
    print("Trying to crack the hash " + hash[0] + "...")
    for i in range(0, 30):
        result = hashlib.sha1(content[i].encode())
        if(result.hexdigest() == hash[0]):
            print("Hash cracked, the decoded string is : " + content[i])


# create and join threads
""" def jointhreads():
    for i in range(0, 30):
        threads[i].join()
 """

""" threads = []

for i in range(0, 30):
    threads.append('thread' + str(i))
    threads[i] = threading.Thread(target=md5)
    threads[i].start()

jointhreads() """

# main thread
print(args.mode)
if(args.mode == 'sha1'):
    sha1()
elif(args.mode == 'md5'):
    md5()
end = timeit.timeit()
print("Time elapsed : " + str(end - start) + " seconds")

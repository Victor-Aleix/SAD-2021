import hashlib,binascii
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
    for i in range(0, len(content)):
        result = hashlib.md5(content[i].encode())
        if(result.hexdigest().lower() == hash[0].lower()):
            print("Hash cracked, the decoded string is : " + content[i])


def sha1():
    print("Trying to crack the hash " + hash[0] + "...")
    for i in range(0, len(content)):
        result = hashlib.sha1(content[i].encode())
        if(result.hexdigest().lower() == hash[0].lower()):
            print("Hash cracked, the decoded string is : " + content[i])


def sha256():
    print("Trying to crack the hash " + hash[0] + "...")
    for i in range(0, len(content)):
        result = hashlib.sha256(content[i].encode())
        if(result.hexdigest().lower() == hash[0].lower()):
            print("Hash cracked, the decoded string is : " + content[i])


def nt():
    print("Trying to crack the hash " + hash[0] + "...")
    for i in range(0, len(content)):
        result = hashlib.new('md4',content[i].encode('utf-16le'))
        if(result.hexdigest().lower() == hash[0].lower()):
            print("Hash cracked, the decoded string is : " + content[i])


# create and join threads
""" def jointhreads():
    for i in range(0, numberofthreads):
        threads[i].join()

jointhreads() """

# main thread
print(args.mode)
if(args.mode == 'sha1'):
    sha1()
elif(args.mode == 'md5'):
    md5()
elif(args.mode == 'sha256'):
    sha256()
elif(args.mode == 'nt'):
    nt()
end = timeit.timeit()
print("Time elapsed : " + str(end - start) + " seconds")

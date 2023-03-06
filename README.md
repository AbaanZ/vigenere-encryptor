# vigenere-encryptor
A multiprocessing program that encrypts/decrypts words using a Vigenère cipher.


The files included in this archive are the Logger, Encryption, and Driver programs, as well as a logfiler.txt file containing a record of a sample run of my program. The Logger file is in charge of handling the recording of the driver activity to the logfiler.txt file by logging each action. The Encryption file handles the heavy lifting with regards to encoding and decoding a given string using a Vigenere Ciphere using a passkey. Finally, the Driver is in charge of communicating between both these processes and managing the flow of information in a centralized program. The logfiler.txt is included for the purpose of viewing one of my own runs of this project, and can be used for testing as well.

This program uses the Process class in java to set up and communicate between the pipes.

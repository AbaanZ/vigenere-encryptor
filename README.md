# vigenere-encryptor
This program uses multiprocessing to make three different programs communicate with each other in an effort to encrypt a string of characters using a Vigenère cipher.
The Driver program is the process that facilitates the communication between the Encryption process and Logger process. The Encryption process does the heavy lfiting
and encrypts the string that the user chooses to enter with the help of a passkey; additionally, a history feature allows the user to pick any previous string to use.
The Logger process documents the actions performed by the user in a .txt file, an example of which I've provided.

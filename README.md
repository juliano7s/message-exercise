# message-exercise

Small message processing program exercise. On this implementation, messages are read from files on folder message_inbox.
Each file must contain a message on its first line. The messages can be one of the following three types:

 - SINGLE:     1 [product] [value] e.g: 1 apple 5.5
 - MULTI:      2 [product] [value] [number_of_sales] e.g 2 apple 5.5 10
 - ADJUSTMENT: 3 [product] [value] [operation] [adjustment_value] e.g 3 apple 4.5 add 1

 The program reads the folder continuously. Once a message file is added to it, it is read, the message is parsed,
 processed (sales are saved and operations are performed) and the file is deleted.

 If an error occurs while parsing or processing the message, a file with error information is added to message_error folder.

 On message_samples there are several examples of message files that can be used to test the program.

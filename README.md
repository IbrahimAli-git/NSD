Networked Software Development

Overview

For this module's assignment I was tasked with building a console-based multithreaded chat application.
This was to be done using the network development techniques I was taught in the lessons
and given further instruction on in the tutorials. This was also to be done using client 
server architecture. The resulting application is to allow clients to interact with 
each other via chat channels and to be able to publish and read messages. Furthermore, these 
interactions were to be recorded.

Key Topics:

C1. Core Functionality  
C2. Quality of the console  
C3. Demonstration  
C4. Extensions

C1. Core Functionality  
The first core functionality I was tasked with implementing was a working server. This server
had to compile and run successfully without any errors. It also had to accept concurrent connections
from multiple clients. I implemented this using a ServerSocket; the user was asked to enter 
the port number to run the server on. This port number was verified before being accepted.
This allowed for the server to run without errors. I then created a class called ClientThreadHandler.
This class implemented the runnable interface and allowed for communication between the Client class
and the server. It also allowed for multiple instances of the client class to run concurrently allowing
for multiple clients to connect with the server in parallel.

C2. Quality of my console  
The second core functionality I was tasked with implementing was the quality of the console.
I was tasked with ensuring that the console is user-friendly and allows the user to interact with the
main functionalities of the program. I have achieved this by implementing a simple menu system in both the client
and server classes. Both allow the user to enter the chosen port numbers and the localhost. It also allows the user to 
view the messages of the channel they have subscribed to. 

C3. Demonstration  
The demonstration is the third core functionality of the assignment. This consists of a video
recording which demonstrates my working program. This can be found in the resources' folder. It can also be viewed on YouTube 
using this link: https://youtu.be/kpK2KHBGPSM 

C4. Extensions  
The extensions are the fourth core functionality of the program. My program does not consist of any extensions.

How to Run the Program:  
1. First ensure that Java is installed on one's computer. 
2. Next ensure that Intellij Idea is installed on one's computer.
3. Start Intellij Idea and open my project on it.
4. Then run the server and input the chosen port number.
5. Then run the client and input the chosen host and port number.
6. To run multiple instances of the client, go to configurations and make sure allow multiple instances is selected. Then simply run the class again.

References:  
1. https://shuspace.shu.ac.uk/ultra/courses/_339673_1/cl/outline
2. https://youtu.be/gLfuZrrfKes
3. https://www.codejava.net/java-se/networking/how-to-create-a-chat-console-application-in-java-using-socket
4. https://www.freecodecamp.org/news/how-to-write-a-good-readme-file/
5. https://stackoverflow.com/questions/6994518/how-to-delete-the-content-of-text-file-without-deleting-itself 


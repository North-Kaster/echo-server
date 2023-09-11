package udp_echo_service;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

/*
Echo Server creates a UDP socket and packet, and sends back whatever the client sends to the server
"Echoing" their
 */
public class EchoServer {
    public static void main(String[] args) throws IOException {

        //setting specific port number so that clients know which port number is needed to contact the server
        DatagramSocket serverSocket = new DatagramSocket(3000);

        while (true) {
            // creating an empty packet from the client. This still contains all standard info like
            // IP addresses and port numbers, but no content
            DatagramPacket clientRequest = new DatagramPacket(new byte[1024], 1024);

            // blocking call. There's a chance the following line could receive something,
            // then return, otherwise, it blocks. It doesn't have to receive
            serverSocket.receive(clientRequest);
            byte[] content = Arrays.copyOf(
                    clientRequest.getData(),
                    clientRequest.getLength());
            System.out.println(new String(content));
            // extracting source IP address and port number from our empty "clientRequest" packet so that we can
            // use that as our destination when we send our server reply to echo back to client
            InetAddress clientIP = clientRequest.getAddress();
            int clientPort = clientRequest.getPort();

            // creating a non-empty packet for our response
            DatagramPacket serverReply = new DatagramPacket(content,
                    content.length,
                    clientIP,
                    clientPort
            );
            serverSocket.send(serverReply);
        }

        //serverSocket.close();
    }
}
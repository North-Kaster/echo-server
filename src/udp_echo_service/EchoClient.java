package udp_echo_service;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class EchoClient {

    public static void main(String[] args) throws IOException {

        if(args.length != 2){
            System.out.println("Syntax: EchoClient <serverIP> <serverPort>");
            return;
        }

        InetAddress serverIP = InetAddress.getByName(args[0]);
        int serverPort = Integer.parseInt(args[1]);

        Scanner keyboard = new Scanner(System.in);
        while(true) {
        String message = keyboard.nextLine();


            //create & send the request packet to server
            //not specifying port number because the client makes the first move and the server extracts the port number
            //from whatever the client sends
            //We don't need to be specific about our port number
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket request = new DatagramPacket(
                    message.getBytes(),
                    message.getBytes().length,
                    serverIP,
                    serverPort
            );
            socket.send(request);

            //receive & process server reply
            DatagramPacket reply = new DatagramPacket(new byte[1024], 1024);
            socket.receive(reply);
            byte[] serverMessage = Arrays.copyOf(
                    reply.getData(),
                    reply.getLength());
            System.out.println(new String(serverMessage));
        }
    }
}

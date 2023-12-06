package com.timbuchalka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // The address of the host we want to connect to, and the port number
        // local host: connect to a server running on the same host, which is our computer.
        // or: 127.0.0.1
        try (Socket socket = new Socket("localhost", 5000)) {
            // Prevents the client from blocking forever:
            // Set a timeout period on a socket, if a socket doesn't respond within the timeout,
            // then we get a java.net.socket timeout exception thrown.
            // In this case, we are going to terminate the application,
            // but in real world, it will depend on the situation. (e.g inform the user, send again)

            socket.setSoTimeout(5000);

            //if you're writing a client that wants to connect to a server that you didn't write
            // the server should tell you the port number that you have to use.
            BufferedReader echoes = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream(), true);


            // Use scanner to read input from the console
            // Once we get the string, write it to the socket
            Scanner scanner = new Scanner(System.in);
            String echoString;
            String response;
            do {
                System.out.println("Enter string to be echoed: ");
                echoString = scanner.nextLine();

                // 会输出到socket.getOutputStream返回的outputstream
                //在client上output就是向服务器发送数据！
                stringToEcho.println(echoString);
                if (!echoString.equals("exit")) {
                    //接收数据并打印
                    response = echoes.readLine();
                    System.out.println(response);
                }
            } while (!echoString.equals("exit"));


        } catch (SocketTimeoutException e){
            System.out.println("Time socket timed out");
        } catch (IOException e){
            System.out.println("Client Error: " + e.getMessage());
        }
    }
}
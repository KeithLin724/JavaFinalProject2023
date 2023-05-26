package online.InternetBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// https://heptadecane.medium.com/file-transfer-via-java-sockets-e8d4f30703a5
// https://stackoverflow.com/questions/9520911/java-sending-and-receiving-file-byte-over-sockets

public class InternetBaseClass {
    protected BufferedReader _inputStream;
    protected PrintWriter _outStream;
    protected Socket _sock;

    /**
     * A constructor.
     * 
     * @param sock
     * @param inputStream
     * @param outputStream
     * @param port
     * @throws IOException
     */
    protected InternetBaseClass(Socket sock) {
        try {
            this._sock = sock;
            this._inputStream = new BufferedReader(new InputStreamReader(this._sock.getInputStream()));
            this._outStream = new PrintWriter(this._sock.getOutputStream(), true);

        } catch (IOException e) {
            this.close();
        }
    }

    /**
     * It sends a message to the server
     * 
     * @param msg The message to send
     */
    public void sendMessagePlug(String msg) {
        this._outStream.println(msg);
    }

    /**
     * It reads a UTF-8 encoded string from the input stream
     * 
     * @return The message that was sent from the client.
     * @throws IOException
     */
    public String recvMessagePlug() throws IOException {
        return this._inputStream.readLine();
    }

    /**
     * It closes the socket, input stream, and output stream
     */
    public void close() {
        try {
            if (this._outStream != null) {
                this._outStream.close();
            }
            if (this._inputStream != null) {
                this._inputStream.close();
            }
            if (this._sock != null) {
                this._sock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

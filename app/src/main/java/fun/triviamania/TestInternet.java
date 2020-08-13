package fun.triviamania;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class TestInternet extends AsyncTask<Integer, Integer, Boolean> {
    public AsyncResponse delegate = null;

    @Override
    protected Boolean doInBackground(Integer... integers) {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;

        } catch (IOException e) {

            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean test) {
        delegate.processFinish(test);
    }
}

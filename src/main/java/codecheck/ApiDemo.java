package codecheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

public class ApiDemo {
    public static final String HOST = "https://api.a3rt.recruit-tech.co.jp/image_influence/v1/meat_score";
    private static final String API_KEY = "8kZsmcvlVnEoicF5oeUjdtwbWBLdxAWJ";
    public static final String CRLF = "\r\n";
    private static final String BOUNDARY = "---*#konasiddkasud#";
    public static final String TWO_HYPHENS = "--";

    public static void main(String[] args) {
        getResult();
    }

    public static void getResult() {
        String result = "";
        try {
            URL url = new URL(HOST);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setUseCaches(false);

            StringBuilder builder = new StringBuilder();
            builder.append(TWO_HYPHENS + BOUNDARY + CRLF);
            builder.append("Content-Disposition: form-data; name=\"apikey\"" + CRLF);
            builder.append(CRLF);
            builder.append(API_KEY + CRLF);
            builder.append(TWO_HYPHENS + BOUNDARY + CRLF);
            builder.append("Content-Disposition: form-data; name=\"predict\"" + CRLF);
            builder.append(CRLF);
            builder.append("1" + CRLF);
            builder.append(TWO_HYPHENS + BOUNDARY + CRLF);
            builder.append("Content-Disposition: form-data; name=\"imagefile\"; filename=\"sample.jpg\"" + CRLF);
            builder.append("Content-Type: image/jpeg" + CRLF);
            builder.append(CRLF);
            File file = new File("sample.jpg");
            FileInputStream fis = new FileInputStream(file);
            StringBuilder lastBuilder = new StringBuilder();
            lastBuilder.append(CRLF + TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + CRLF);

            con.setChunkedStreamingMode(0);

            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(builder.toString());
            int bytesAvailable = fis.available();
            int maxBufferSize = 1 * 1024 * 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];
            int bytesRead = fis.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                out.write(buffer, 0, bufferSize);
                bytesAvailable = fis.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fis.read(buffer, 0, bufferSize);
            }
            out.writeBytes(lastBuilder.toString());
            out.flush();
            out.close();
            con.connect();
            System.out.println("OK1");
            InputStream iS = con.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(iS));
            System.out.println("OK2");
            String tmp = "";
            while ((tmp = in.readLine()) != null) {
                result += tmp;
            }
            in.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}

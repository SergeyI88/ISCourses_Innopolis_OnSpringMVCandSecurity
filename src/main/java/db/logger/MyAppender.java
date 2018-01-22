package db.logger;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import java.io.*;

public class MyAppender extends AppenderSkeleton {
    private String file;
    private int countStrings;

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public int getCountStrings() {
        return countStrings;
    }

    public void setCountStrings(int countStrings) {
        this.countStrings = countStrings;
    }

    @Override
    protected void append(LoggingEvent event) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String count = reader.readLine();
            int countStrings = 0;
            while (count != null) {
                count = reader.readLine();
                countStrings++;
            }
            if (countStrings < this.countStrings) {
                FileWriter writer = new FileWriter(file, true);
                writer.write(String.format("%s", layout.format(event)));
                writer.flush();
                writer.write(System.getProperty("line.separator"));
                writer.flush();
                writer.close();
            } else {
                String newFile = file + 1;
                FileWriter writer = new FileWriter(newFile, true);
                writer.write(String.format("%s", layout.format(event)));
                writer.flush();
                writer.write(System.getProperty("line.separator"));
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return true;
    }
}

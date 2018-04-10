package com.message;

import java.io.*;

/**
 * Reads messages from files of a folder that is passed on the constructor. Each file represents a single message which
 * must be written on the first line of the file.
 */
public class FileMessageReader implements MessageReader {

    private String messageFolderPath;

    public FileMessageReader(String messageFolderPath) {
        this.messageFolderPath = messageFolderPath;
    }

    public String readNextMessage() {
        File nextMessageFile = getNextFile();
        try {
            if (nextMessageFile != null) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(nextMessageFile));
                String messageRaw = bufferedReader.readLine();
                bufferedReader.close();
                deleteReadFile(nextMessageFile);
                return messageRaw;
            }
        } catch (FileNotFoundException e) {
            //ignore file not found and skip reading
        } catch (IOException e) {
            logFailReadingMessage(nextMessageFile, e);
        }
        return null;
    }

    private File getNextFile() {
        File messageFolder = new File(messageFolderPath);
        File[] messageFiles = messageFolder.listFiles();
        if (messageFiles != null && messageFiles.length > 0) {
            File nextMessageFile = messageFiles[0];
            return nextMessageFile;
        }
        return null;
    }

    private void deleteReadFile(File nextMessageFile) {
        nextMessageFile.delete();
    }

    private void logFailReadingMessage(File file, IOException e) {
        System.out.println("Unexpected failure reading message from file: " + file);
        e.printStackTrace();
    }
}

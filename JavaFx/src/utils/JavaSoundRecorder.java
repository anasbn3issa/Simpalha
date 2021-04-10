/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javafx.concurrent.Task;
import javax.sound.sampled.*;

/**
 * A sample program is to demonstrate how to record sound in Java author:
 * www.codejava.net
 * http://www.codejava.net/coding/capture-and-record-sound-into-wav-file-with-java-sound-api
 */
public class JavaSoundRecorder extends Task<Void>
{

    // record duration, in milliseconds
    static final long RECORD_TIME = 60000;  // 1 minute

    // path of the wav file
        
        
        
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    // the line from which audio data is captured
    TargetDataLine line;
    private File wavFile;

    @Override
    protected Void call() throws Exception
    {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing

            System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");
Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

            String dir = System.getProperty("user.dir");//get project source path
            wavFile = new File(dir + "\\ressources\\"+ sdf1.format(timestamp) +"RecordAudio.wav");//add the full path /ressources + file name
            //check if folder ressources is created, sinon create it
            File file = new File(dir + "\\ressources");
            file.mkdirs();
         
            // start recording
            AudioSystem.write(ais, fileType, wavFile);

        }
        catch (LineUnavailableException | IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat()
    {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    public String finish()
    {
        line.stop();
        line.close();
        System.out.println("Finished");
        return wavFile.getName();
    }

}


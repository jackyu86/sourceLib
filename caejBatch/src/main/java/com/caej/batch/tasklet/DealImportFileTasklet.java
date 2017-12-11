package com.caej.batch.tasklet;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.util.StringUtils;

import java.io.*;

/**
 * A tasklet that logs product information
 */
public class DealImportFileTasklet implements Tasklet
{
    private String inputFile;
    private String outFilePath;
    private PrintWriter pw;

    public String getOutFilePath() {
        return outFilePath;
    }

    public void setOutFilePath(String outFilePath) {
        this.outFilePath = outFilePath;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception
    {

      //  FileUtils.copyFileToDirectory( new File( inputFile ), archiveDir );

        format(inputFile,outFilePath);
        // We're done...
        return RepeatStatus.FINISHED;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }



    public static void format(String fromfile,String outFilePath) {

        File temp = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            temp = File.createTempFile("temp", "temp");
            pw = new PrintWriter(temp);
            br = new BufferedReader(new FileReader(new File(fromfile)));
            int lineNum =0;
            int fieldNum = 0;
            while (br.ready()) {
                String line = br.readLine();
                lineNum++;
                System.out.println(line);
                if (lineNum == 10) {
                    fieldNum = new Integer(line);
                }
                if (lineNum >fieldNum+11 && !"OFDCFEND".equals(line)) {
                    pw.println(line);
                }
            }
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            safeClose(br);
            safeClose(pw);
            if (temp != null) {
                //file.delete();
                temp.renameTo(new File(outFilePath +"\\" +fromfile.substring(fromfile.length()-6)));
            }
        }
    }
    private static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {
        try {
            DealImportFileTasklet task =  new DealImportFileTasklet();
            task.setInputFile("C:\\TEMP\\testData\\OFD_PA_2PA_20171115_04.TXT");
            task.execute(null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

